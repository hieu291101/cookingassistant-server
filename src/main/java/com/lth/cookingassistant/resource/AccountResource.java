package com.lth.cookingassistant.resource;
import com.lth.cookingassistant.exception.CANotFoundException;
import com.lth.cookingassistant.exception.ResourceNotFoundException;
import com.lth.cookingassistant.model.Account;
import com.lth.cookingassistant.repo.AccountRepo;
import com.lth.cookingassistant.request.LoginRequest;
import com.lth.cookingassistant.response.Response;
import com.lth.cookingassistant.security.CurrentUser;
import com.lth.cookingassistant.security.UserPrincipal;
import com.lth.cookingassistant.service.AccountService;

import com.lth.cookingassistant.utils.AccountUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@CrossOrigin("http://localhost:3001")
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountResource {
	private final AccountService accountService;

	private final AccountRepo accountRepo;

	private final AccountUtils util;

	@GetMapping("/profile")
	@PreAuthorize("hasRole('USER')")
	public Account getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		return accountRepo.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
	}

	@GetMapping()
	public ResponseEntity<Response> getALlAccounts(){
			ResponseEntity<Response> res;
			try{
				res = ResponseEntity.ok(Response.builder()
						.timeStamp(now())
						.data(Map.of("account", accountService.list(20)))
						.message("Account retrieved")
						.status(OK)
						.statusCode(OK.value())
						.build()
				);
			} catch(Exception e) {
				e.printStackTrace();
				res = ResponseEntity.internalServerError().body(Response.builder()
						.timeStamp(now())
						.message("Unable to get account")
						.status(INTERNAL_SERVER_ERROR)
						.statusCode(INTERNAL_SERVER_ERROR.value())
						.build());
			}
			return res;
	}

	@GetMapping("/pagination")
	public ResponseEntity<Response> getAccounts(@RequestParam(value = "page", required = true) Integer page){
		ResponseEntity<Response> res;
		try{
			res = ResponseEntity.ok(Response.builder()
					.timeStamp(now())
					.data(Map.of("account", accountService.list(page, 30)))
					.message("Account retrieved")
					.status(OK)
					.statusCode(OK.value())
					.build()
			);
		} catch(Exception e) {
			e.printStackTrace();
			res = ResponseEntity.internalServerError().body(Response.builder()
					.timeStamp(now())
					.message("Unable to get account")
					.status(INTERNAL_SERVER_ERROR)
					.statusCode(INTERNAL_SERVER_ERROR.value())
					.build());
		}
		return res;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response> getAccount(@PathVariable Long id){
		ResponseEntity<Response> res;
		try {
			res = ResponseEntity.ok(Response.builder()
					.timeStamp(now())
					.data(Map.of("account", accountService.get(id)))
					.message("Account retrieved")
					.status(OK)
					.statusCode(OK.value())
					.build()
			);
		} catch (CANotFoundException cfe) {
			throw cfe;
		} catch (Exception e) {
			e.printStackTrace();
			res = ResponseEntity.internalServerError().body(Response.builder()
					.timeStamp(now())
					.message("Unable to get account")
					.status(INTERNAL_SERVER_ERROR)
					.statusCode(INTERNAL_SERVER_ERROR.value())
					.build());
		}
		return res;
	}

	@PostMapping ()
	public ResponseEntity<Response> saveAccount(@RequestBody @Valid Account account){
		ResponseEntity<Response> res;
		try {
			res = ResponseEntity.ok(Response.builder()
					.timeStamp(now())
					.data(Map.of("account", accountService.create(account)))
					.message("Account created")
					.status(OK)
					.statusCode(OK.value())
					.build());
		} catch(Exception e) {
			e.printStackTrace();
			res = ResponseEntity.internalServerError().body(Response.builder()
					.timeStamp(now())
					.message("Unable to save account")
					.status(INTERNAL_SERVER_ERROR)
					.statusCode(INTERNAL_SERVER_ERROR.value())
					.build());
		}
		return res;
	}

	@PostMapping ("/login")
	public ResponseEntity<Response> loginAccount(@RequestBody @Valid LoginRequest account){
		ResponseEntity<Response> res;
		try {
			if (accountService.isSignInSuccess(account))
				res = ResponseEntity.ok(Response.builder()
						.timeStamp(now())
						.data(Map.of("account", accountService.findByUsername(account.getUsername())))
						.message("Login successful")
						.status(OK)
						.statusCode(OK.value())
						.build());
			else
				throw new Exception("Username or password is invalid");
		} catch(Exception e) {
			e.printStackTrace();
			res = ResponseEntity.internalServerError().body(Response.builder()
					.timeStamp(now())
					.message(e.getMessage())
					.status(INTERNAL_SERVER_ERROR)
					.statusCode(INTERNAL_SERVER_ERROR.value())
					.build());
		}
		return res;
	}

	@DeleteMapping ("/{id}")
	public ResponseEntity<Response> deleteAccount(@PathVariable("id") @Valid Long id){
		ResponseEntity<Response> res;
		try {
			res = ResponseEntity.ok(Response.builder()
					.timeStamp(now())
					.data(Map.of("account", accountService.delete(id)))
					.message("Account retrieved")
					.status(OK)
					.statusCode(OK.value())
					.build()
			);
		} catch (CANotFoundException cfe) {
			throw cfe;
		} catch (Exception e) {
			e.printStackTrace();
			res = ResponseEntity.internalServerError().body(Response.builder()
					.timeStamp(now())
					.message("Unable to delete account")
					.status(INTERNAL_SERVER_ERROR)
					.statusCode(INTERNAL_SERVER_ERROR.value())
					.build());
		}
		return res;
	}


	@PutMapping("/{id}")
	public ResponseEntity<Response> updateAccount(@PathVariable Long id, @RequestBody Account account){

		ResponseEntity<Response> rep;
		try {
			//db Object
			Account ac = accountService.get(id);
			//copy non-null values from request to Database object
			util.copyNonNullValues(account, ac);

			rep = ResponseEntity.ok(Response.builder()
					.timeStamp(now())
					.data(Map.of("account", accountService.update(ac)))
					.message("Account retrieved")
					.status(RESET_CONTENT)
					.statusCode(RESET_CONTENT.value())
					.build());

		} catch (CANotFoundException cfe) {
			throw cfe;
		} catch (Exception e) {
			e.printStackTrace();
			rep = ResponseEntity.internalServerError().body(Response.builder()
					.timeStamp(now())
					.message("Unable to update account")
					.status(INTERNAL_SERVER_ERROR)
					.statusCode(INTERNAL_SERVER_ERROR.value())
					.build());
		}
		return rep;
	}
}
