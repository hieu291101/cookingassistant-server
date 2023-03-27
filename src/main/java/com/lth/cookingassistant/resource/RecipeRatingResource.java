package com.lth.cookingassistant.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lth.cookingassistant.request.RecipeRatingIdRequest;
import com.lth.cookingassistant.request.RecipeRatingRequest;
import com.lth.cookingassistant.response.Response;
import com.lth.cookingassistant.service.RecipeRatingService;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

import java.util.Map;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rating")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class RecipeRatingResource {
    private final RecipeRatingService recipeRatingService;

    @PostMapping()
    public ResponseEntity<Response> getStar(@RequestBody RecipeRatingIdRequest recipeRatingIdRequest){
        ResponseEntity<Response> res;
        try{
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("star", recipeRatingService.getAverageStar(recipeRatingIdRequest)))
                    .message("Star retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to get star")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @PostMapping("/add")
    public ResponseEntity<Response> saveRating(@RequestBody @Valid RecipeRatingRequest recipeRatingRequest){
        ResponseEntity<Response> res;
        try {
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("rating",
                        recipeRatingService.create(recipeRatingRequest)
                    ))
                    .message("rating created")
                    .status(OK)
                    .statusCode(OK.value())
                    .build());
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to save rating")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }
}
