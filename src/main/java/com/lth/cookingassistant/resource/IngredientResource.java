package com.lth.cookingassistant.resource;

import com.lth.cookingassistant.exception.CANotFoundException;
import com.lth.cookingassistant.model.Ingredient;
import com.lth.cookingassistant.response.PaginationResponse;
import com.lth.cookingassistant.response.Response;
import com.lth.cookingassistant.service.IngredientService;
import com.lth.cookingassistant.utils.IngredientUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import javax.validation.Valid;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ingredient")
@RequiredArgsConstructor
public class IngredientResource {
    private final IngredientService ingredientService;

    private final IngredientUtils util;

    @GetMapping()
    public ResponseEntity<Response> getAllIngredient(){
        ResponseEntity<Response> res;
        try{
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("ingredient", ingredientService.getAllIngredient()))
                    .message("Ingredient retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to get ingredient")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @GetMapping("/pagination")
    public ResponseEntity<Response> getIngredients(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "page", required = true) Integer page
    ){
        ResponseEntity<Response> res;
        try{
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("ingredient", ingredientService.list(page, limit)))
                    .pagination(
                            PaginationResponse
                                    .builder()
                                    .page(page)
                                    .limit(limit)
                                    .totalRows(ingredientService.countIngredient())
                                    .build()
                    )
                    .message("Ingredient retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to get ingredient")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @GetMapping("/getIngredientBy")
    public ResponseEntity<Response> getIngredientBy(@RequestParam(value = "keyword", required = true) String keyword ){
        ResponseEntity<Response> res;
        try{
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("ingredient", ingredientService.searchIngredients(keyword)))
                    .message("Ingredient retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to get ingredient")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @PostMapping ("/create")
    public ResponseEntity<Response> saveIngredient(@RequestBody @Valid Ingredient ingredient){
        ResponseEntity<Response> res;
        try {
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("ingredient", ingredientService.create(ingredient)))

                    .message("ingredient created")
                    .status(OK)
                    .statusCode(OK.value())
                    .build());
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to save ingredient")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<Response> updateMeasure(@PathVariable Long id, @RequestBody Ingredient ingredient){

        ResponseEntity<Response> rep;
        try {
            //db Object
            Ingredient ing = ingredientService.get(id);
            //copy non-null values from request to Database object
            util.copyNonNullValues(ingredient, ing);
            
            rep = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("ingredient", ingredientService.update(ing)))
                    .message("ingredient retrieved")
                    .status(RESET_CONTENT)
                    .statusCode(RESET_CONTENT.value())
                    .build());

        } catch (CANotFoundException cfe) {
            throw cfe;
        } catch (Exception e) {
            e.printStackTrace();
            rep = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to update ingredient")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return rep;
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<Response> deleteMeasure(@PathVariable("id") @Valid long id){
        ResponseEntity<Response> res;
        try {
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("ingredient", ingredientService.delete(id)))
                    .message("ingredient deleted")
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
                    .message("Unable to delete ingredient")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }
}
