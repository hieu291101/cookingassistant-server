package com.lth.cookingassistant.resource;

import com.lth.cookingassistant.exception.CANotFoundException;
import com.lth.cookingassistant.response.Response;
import com.lth.cookingassistant.service.RecipeDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/recipe_detail")
@RequiredArgsConstructor
public class RecipeDetailResource {
    private final RecipeDetailService recipeDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<Response> getRecipeDetail(@PathVariable Long id){
        ResponseEntity<Response> res;
        try {
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("recipeDetail", recipeDetailService.getIngredient(id)))
                    .message("Recipe detail retrieved")
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
                    .message("Unable to get recipe detail")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }
}
