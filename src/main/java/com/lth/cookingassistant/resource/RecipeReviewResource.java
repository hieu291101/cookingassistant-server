package com.lth.cookingassistant.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lth.cookingassistant.exception.CANotFoundException;
import com.lth.cookingassistant.model.Account;
import com.lth.cookingassistant.model.Profile;
import com.lth.cookingassistant.model.RecipeReview;
import com.lth.cookingassistant.request.RecipeReviewIdRequest;
import com.lth.cookingassistant.request.RecipeReviewRequest;
import com.lth.cookingassistant.response.Response;
import com.lth.cookingassistant.response.ReviewResponse;
import com.lth.cookingassistant.service.AccountService;
import com.lth.cookingassistant.service.RecipeReviewService;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/review")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Slf4j
public class RecipeReviewResource {
    private final RecipeReviewService recipeReviewService;
    private final AccountService accountService;

    @GetMapping("/{id}")
	public ResponseEntity<Response> getReview(@PathVariable Long id){
		ResponseEntity<Response> res;
		try {
            Collection<RecipeReview> recipes = recipeReviewService.getReview(id);
            Collection<ReviewResponse> reviews = new ArrayList<ReviewResponse>();
            if(!recipes.isEmpty()){
                for (RecipeReview recipeReview : recipes) {
                    Account ac = accountService.findByRecipeReviews(recipeReview);
                    log.info("asjfkl {}", ac.getAccountId());
                    Profile prf = new Profile();
                    if(ac != null && ac.getProfiles().size() > 0)
                        prf = ac.getProfiles().get(0);
                    reviews.add(ReviewResponse.builder()
                                            .recipeReviewId(recipeReview.getRecipeReviewId())
                                            .reviewText(recipeReview.getReviewText())
                                            .photoPath(prf.getPhotoPath() != null ? prf.getPhotoPath() : "")
                                            .name(
                                                    prf.getFirstName() != null && prf.getLastName() != null ? 
                                                    prf.getFirstName() + " " + prf.getLastName() :
                                                    ac.getUsername()
                                                )
                                            .createdDate(recipeReview.getCreatedDate())
                                            .build()
                    ); 
                }

            }
			res = ResponseEntity.ok(Response.builder()
					.timeStamp(now())
					.data(Map.of("review", reviews))
					.message("Review retrieved")
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
					.message("Unable to get review")
					.status(INTERNAL_SERVER_ERROR)
					.statusCode(INTERNAL_SERVER_ERROR.value())
					.build());
		}
		return res;
	}

    @PostMapping("/add")
    public ResponseEntity<Response> saveReview(@RequestBody @Valid RecipeReviewRequest recipeReviewRequest){
        ResponseEntity<Response> res;
        try {
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("review",
                        recipeReviewService.create(recipeReviewRequest)
                    ))
                    .message("Review created")
                    .status(OK)
                    .statusCode(OK.value())
                    .build());
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to save review")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }
}
