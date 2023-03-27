package com.lth.cookingassistant.resource;

import com.lth.cookingassistant.exception.CANotFoundException;
import com.lth.cookingassistant.model.Recipe;
import com.lth.cookingassistant.model.RecipesDetail;
import com.lth.cookingassistant.request.RecipeFilter;
import com.lth.cookingassistant.request.RecipeRequest;
import com.lth.cookingassistant.response.PaginationResponse;
import com.lth.cookingassistant.response.RecipeResponse;
import com.lth.cookingassistant.response.Response;
import com.lth.cookingassistant.service.RecipeDetailService;
import com.lth.cookingassistant.service.RecipeService;
import com.lth.cookingassistant.utils.GenericUtils;
import com.lth.cookingassistant.utils.RecipeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class RecipeResource {
    private final RecipeService recipeService;

    private final RecipeDetailService recipeDetailService;

    private final RecipeUtils util;

    @GetMapping()
    public ResponseEntity<Response> getALlRecipes(){
        ResponseEntity<Response> res;
        try{
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("recipe", recipeService.list(30)))
                    .message("Recipe retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to get recipe")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @GetMapping("/suggest")
    public ResponseEntity<Response> suggestRecipes(@RequestParam(value = "page", required = false) Integer page,
                                                   @RequestParam(required = false) List<String> ingredientName,
                                                   @RequestParam(value = "total_time") int totalTime){
        ResponseEntity<Response> res;
        try{
        
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("recipe", recipeDetailService.suggestIngredient(ingredientName, totalTime)))
                    .message("Recipe retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to get recipe")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @GetMapping("/pagination")
    public ResponseEntity<Response> getRecipes(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "page", required = true) Integer page
    ){
        ResponseEntity<Response> res;
        try{
            Collection<Recipe> recipes = recipeService.list(page, limit);
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("recipe", recipes.stream().map(r -> {
                        return RecipeResponse.builder()
                                            .recipeId(r.getRecipeId())
                                            .accessModifier(r.getAccessModifier())
                                            .activeTime(r.getActiveTime())
                                            .briefDescription(r.getBriefDescription())
                                            .createdDate(r.getCreatedDate())
                                            .cuisine(r.getCuisine())
                                            .mainIngredient(r.getMainIngredient())
                                            .photoPath(r.getPhotoPath())
                                            .preparation(r.getPreparation())
                                            .title(r.getTitle())
                                            .totalTime(r.getTotalTime())
                                            .yields(r.getYields())
                                            .username(r.getAccount().getUsername())
                                            .category(
                                                r.getCategoriesSub()
                                                    .getCategoriesMain()
                                                    .getName() + " - " + 
                                                r.getCategoriesSub().getName())
                                            .build();
                    })))
                    .pagination(
                            PaginationResponse
                                    .builder()
                                    .page(page)
                                    .limit(limit)
                                    .totalRows(recipeService.countRecipe())
                                    .build()
                    )
                    .message("Recipe retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to get recipe")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchRecipes(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(required = false) String title
    ){
        ResponseEntity<Response> res;
        try{
            Collection<Recipe> recipes = recipeService.searchByTitle(title, page, limit);
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("recipe", recipes))
                    .pagination(
                            PaginationResponse
                            .builder()
                            .page(page)
                            .limit(limit)
                            .totalRows(recipes.size())
                            .build()
                    )
                    .message("Recipe retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to get recipe")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @GetMapping("/filter")
    public ResponseEntity<Response> getRecipes(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(required = false) String title,
            @RequestParam(value = "total_time", required = true) int totalTime,
            @RequestParam(required = false) String cuisine
    ){
        ResponseEntity<Response> res;
        try{
            Collection<Recipe> recipes = recipeService.filter(page, limit, title, totalTime, cuisine);
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("recipe", recipes))
                    .pagination(
                            PaginationResponse
                            .builder()
                            .page(page)
                            .limit(limit)
                            .totalRows(recipes.size())
                            .build()
                    )
                    .message("Recipe retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to get recipe")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getRecipe(@PathVariable Long id){
        ResponseEntity<Response> res;
        try {
            Recipe r =  recipeService.get(id);
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("recipe", 
                        RecipeResponse.builder()
                                    .recipeId(r.getRecipeId())
                                    .accessModifier(r.getAccessModifier())
                                    .activeTime(r.getActiveTime())
                                    .briefDescription(r.getBriefDescription())
                                    .createdDate(r.getCreatedDate())
                                    .cuisine(r.getCuisine())
                                    .mainIngredient(r.getMainIngredient())
                                    .photoPath(r.getPhotoPath())
                                    .preparation(r.getPreparation())
                                    .title(r.getTitle())
                                    .totalTime(r.getTotalTime())
                                    .yields(r.getYields())
                                    .username(r.getAccount().getUsername())
                                    .category(
                                        r.getCategoriesSub()
                                            .getCategoriesMain()
                                            .getName() + " - " + 
                                        r.getCategoriesSub().getName())
                                    .build()))
                    .message("Recipe retrieved")
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
                    .message("Unable to get recipe")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @PostMapping ("/create")
    public ResponseEntity<Response> saveRecipe(@RequestBody @Valid RecipeRequest recipeRequest){
        ResponseEntity<Response> res;
        try {
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("recipe", recipeService.create(recipeRequest)))
                    .message("Recipe created")
                    .status(OK)
                    .statusCode(OK.value())
                    .build());
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to save recipe")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<Response> deleteRecipe(@PathVariable("id") @Valid Long id){
        ResponseEntity<Response> res;
        try {
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("recipe", recipeService.delete(id)))
                    .message("Recipe retrieved")
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
                    .message("Unable to delete recipe")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }


    @PutMapping("/{id}")
    public ResponseEntity<Response> updateRecipe(@PathVariable Long id, @RequestBody RecipeRequest recipeRequest){

        ResponseEntity<Response> rep;
        try {
            //db Object
            Recipe rc = recipeService.get(id);
            //copy non-null values from request to Database object
            util.copyValuesFromRequest(recipeRequest, rc);

            rep = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("Recipe", recipeService.update(rc)))
                    .message("Recipe retrieved")
                    .status(RESET_CONTENT)
                    .statusCode(RESET_CONTENT.value())
                    .build());

        } catch (CANotFoundException cfe) {
            throw cfe;
        } catch (Exception e) {
            e.printStackTrace();
            rep = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to update recipe")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return rep;
    }
}
