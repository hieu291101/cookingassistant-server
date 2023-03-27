package com.lth.cookingassistant.resource;

import com.lth.cookingassistant.mail.EmailService;
import com.lth.cookingassistant.model.*;
import com.lth.cookingassistant.repo.AccountRepo;
import com.lth.cookingassistant.repo.RoleRepo;
import com.lth.cookingassistant.request.*;
import com.lth.cookingassistant.response.GenericResponse;
//import com.lth.cookingassistant.security.CurrentUser;
import com.lth.cookingassistant.security.jwt.JwtTokenProvider;
import com.lth.cookingassistant.response.UserInfoResponse;
import com.lth.cookingassistant.response.MessageResponse;
import com.lth.cookingassistant.security.services.SecurityUserService;
import com.lth.cookingassistant.security.services.UserDetailsImpl;
import com.lth.cookingassistant.service.AccountService;
import com.lth.cookingassistant.service.PasswordResetTokenService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@Api( tags = "Auth")
public class AuthResource {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    AccountService accountService;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    PasswordResetTokenService passwordResetTokenService;

    @Autowired
    SecurityUserService securityUserService;

    @Autowired
    EmailService emailService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        log.info("Saving a new account: {}", authentication);
        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());


        return ResponseEntity.ok()
                .body(Map.of("data", new UserInfoResponse(jwt, userDetails.getId(),
                        userDetails.getUsername(), userDetails.getEmail(), roles)));
    }

//    @PostMapping("/signin/facebook")
//    public ResponseEntity<?> authenticateUserFacebook(@Valid @RequestBody LoginRequest loginRequest) {
//
//        // Xác thực từ username và password.
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getUsername(),
//                        loginRequest.getPassword()
//                )
//        );
//        log.info("Saving a new account: {}", authentication);
//        // Nếu không xảy ra exception tức là thông tin hợp lệ
//        // Set thông tin authentication vào Security Context
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//
//        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
//                .collect(Collectors.toList());
//
//        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
//
//        //Trả về jwt cho người dùng.
//        ResponseCookie jwtCookie = tokenProvider.generateJwtCookie(userDetails);
//
//        ResponseCookie jwtRefreshCookie = tokenProvider.generateRefreshJwtCookie(refreshToken.getToken());
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
//                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
//                .body(Map.of("data", new UserInfoResponse(userDetails.getId(),
//                        userDetails.getUsername(), userDetails.getEmail(), roles)));
//    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (accountRepo.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (accountRepo.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        Account account = new Account();
        account.setUsername(signUpRequest.getUsername());
        account.setEmail(signUpRequest.getEmail());
        account.setPassword(encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepo.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepo.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepo.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepo.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        account.setRoles(roles);
        account.setProvider(EProvider.local);
        account.setProviderId(EProvider.local.toString());
        accountRepo.save(account);

        return ResponseEntity.ok(Map.of("data", new MessageResponse("User registered successfully!")));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(principle.toString());
        if (!principle.toString().equals("anonymousUser") ){
            Long userId = ((UserDetailsImpl) principle).getId();
            log.info(" id {}",userId);
        }

        return ResponseEntity.ok()
                .body(new MessageResponse("You've been signed out!"));
    }

    @PostMapping("/sendForgotPasswordEmail")
    public ResponseEntity<?> sendForgotPasswordEmail(@Valid @RequestBody ForgotPasswordRequest request) {

        Account account = accountRepo.findByEmail(request.getForgotPasswordEmail()).get();
        PasswordRefreshToken passwordResetToken = passwordResetTokenService.createPasswordResetToken(account.getAccountId());

        if(accountRepo.existsByEmail(request.getForgotPasswordEmail())) {
            String url = "http://localhost:3001" + "/user/changePassword?token=" + passwordResetToken.getToken();
            String message = emailService.sendSimpleMail(passwordResetToken.getAccount().getEmail(),
                    "Here's the link to reset your password - " + account.getUsername(), url);

            return ResponseEntity.ok(Map.of("data",new MessageResponse(message)));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    //patch mapping
    @PostMapping("/user/savePassword")
    public GenericResponse savePassword(
            final Locale locale,
            @RequestParam("token") String token,
            @Valid ChangePasswordRequest changePasswordRequest
    ) {

        String result = securityUserService.validatePasswordResetToken(token);

        if(result != null) {
            return new GenericResponse("auth.message." + result,locale.toString());
        }

        Optional<Account> account = accountRepo.getAccountByPasswordResetToken(token);
        if(account.isPresent()) {
            accountService.changeAccountPassword(account.get(), changePasswordRequest.getNewPassword());
            return new GenericResponse("message.resetPasswordSuc", locale.toString());
        } else {
            return new GenericResponse("auth.message.invalid", locale.toString());
        }
    }

    // Api /api/random yêu cầu phải xác thực mới có thể request
    @GetMapping("/random")
    public MessageResponse randomStuff(){
        return new MessageResponse("JWT Hợp lệ mới có thể thấy được message này");
    }

}
