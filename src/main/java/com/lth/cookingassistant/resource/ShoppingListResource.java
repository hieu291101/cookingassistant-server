package com.lth.cookingassistant.resource;

import com.lth.cookingassistant.exception.CANotFoundException;
import com.lth.cookingassistant.model.Ingredient;
import com.lth.cookingassistant.model.RecipesDetail;
import com.lth.cookingassistant.model.ShoppingList;
import com.lth.cookingassistant.model.ShoppingListPK;
import com.lth.cookingassistant.request.AddToShoppingRequest;
import com.lth.cookingassistant.request.ShoppingListRequest;
import com.lth.cookingassistant.response.Response;
import com.lth.cookingassistant.response.ShoppingListResponse;
import com.lth.cookingassistant.service.AccountService;
import com.lth.cookingassistant.service.RecipeDetailService;
import com.lth.cookingassistant.service.ShoppingListService;
import com.lth.cookingassistant.utils.ShoppingListUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/shoppinglist")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class ShoppingListResource {
    private final ShoppingListService shoppingListService;
    private final RecipeDetailService recipeDetailService;
    private final AccountService accountService;
    private final ShoppingListUtils util;

    @PostMapping("/{id}")
    public ResponseEntity<Response> getALlShoppingList(@PathVariable Long id){
        ResponseEntity<Response> res;
        try{
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("shoppingList", shoppingListService.list(30, id)
                            .stream().map(shoppingList -> ShoppingListResponse.builder()
                                    .ingredientName(shoppingList.getId().getIngredientName())
                                    .note(shoppingList.getNote())
                                    .measure(shoppingList.getMeasure())
                                    .quantity(shoppingList.getQuantity())
                                    .build())
                    ))
                    .message("shoppingList retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to get shoppingList")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @PostMapping("/pagination/{id}")
    public ResponseEntity<Response> getShoppingList(@RequestParam(value = "page", required = true) Integer page, @PathVariable Long id){
        ResponseEntity<Response> res;
        try{
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("shoppingList", shoppingListService.list(page, 30, id)
                            .stream().map(shoppingList -> ShoppingListResponse.builder()
                                    .ingredientName(shoppingList.getId().getIngredientName())
                                    .note(shoppingList.getNote())
                                    .measure(shoppingList.getMeasure())
                                    .quantity(shoppingList.getQuantity())
                                    .build())
                    ))
                    .message("shoppingList retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to get shoppingList")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @PostMapping("/add/all")
    public ResponseEntity<Response> saveShoppingList(@RequestBody AddToShoppingRequest addToShoppingRequest){
        ResponseEntity<Response> res;
        try {
            List<ShoppingList> shoppingList = new ArrayList<>();
            Collection<RecipesDetail> recipesDetails = recipeDetailService.getIngredient(addToShoppingRequest.getRecipeId());
            if(recipesDetails.size() == 0 ) 
                return null;
                recipesDetails.stream().forEach(recipesDetail -> {
                    ShoppingList spl = new ShoppingList();
                    if(!recipesDetail.getId().getIngredientName().isEmpty()){
                        ShoppingListPK splPK = new ShoppingListPK();
                        splPK.setIngredientName(recipesDetail.getId().getIngredientName());
                        splPK.setAccountId(addToShoppingRequest.getAccountId());
                        spl.setId(splPK);
                    }
                
                    spl.setMeasure(recipesDetail.getMeasure());
                    
                    spl.setQuantity(recipesDetail.getQuantity());

                    spl.setNote("");
                    spl.setAccount(accountService.get(addToShoppingRequest.getAccountId()));
                    shoppingList.add(spl);
                });
                
                
            List<ShoppingList> result = shoppingListService.createAll(shoppingList, addToShoppingRequest.getAccountId());    
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("shoppingList",
                        result.stream().map(sl ->ShoppingListResponse.builder()
                                    .ingredientName(sl.getId().getIngredientName())
                                    .note(sl.getNote())
                                    .measure(sl.getMeasure())
                                    .quantity(sl.getQuantity())
                                    .build())
                    ))
                    .message("ShoppingList created")
                    .status(OK)
                    .statusCode(OK.value())
                    .build());
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to save shoppingList")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @PostMapping("/add")
    public ResponseEntity<Response> saveShoppingList(@RequestBody @Valid ShoppingListRequest shoppingListRequest){
        ResponseEntity<Response> res;
        try {
            ShoppingList shoppingList = new ShoppingList();
            util.copyValuesFromRequest(shoppingListRequest, shoppingList);
            shoppingList = shoppingListService.create(shoppingList);
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("shoppingList",
                            ShoppingListResponse.builder()
                                    .ingredientName(shoppingList.getId().getIngredientName())
                                    .note(shoppingList.getNote())
                                    .measure(shoppingList.getMeasure())
                                    .quantity(shoppingList.getQuantity())
                                    .build()
                    ))
                    .message("ShoppingList created")
                    .status(OK)
                    .statusCode(OK.value())
                    .build());
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to save shoppingList")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateShoppingList(
        @PathVariable Long id, 
        @RequestParam(value = "ingredient_name", required = true) String ingredientName, 
        @RequestBody @Valid ShoppingListRequest shoppingListRequest
        ){
        ResponseEntity<Response> res;
        try {
            ShoppingList shoppingList = shoppingListService.get(id, ingredientName);
            util.copyValuesFromRequest(shoppingListRequest, shoppingList);
            shoppingList = shoppingListService.update(shoppingList);
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("shoppingList",
                            ShoppingListResponse.builder()
                                    .ingredientName(shoppingList.getId().getIngredientName())
                                    .note(shoppingList.getNote())
                                    .measure(shoppingList.getMeasure())
                                    .quantity(shoppingList.getQuantity())
                                    .build()
                    ))
                    .message("Shopping updated successfully")
					.status(HttpStatus.RESET_CONTENT)
					.statusCode(HttpStatus.RESET_CONTENT.value())
					.build());
        } catch (CANotFoundException cfe) {
            throw cfe;
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to update shoppingList")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteShoppingList(
            @PathVariable("id") @Valid Long id,
            @RequestParam(value = "ingredient_name") String ingredientName
    ){
        ResponseEntity<Response> res;
        try {
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("shoppingList", shoppingListService.delete(id, ingredientName)))
                    .message("ShoppingList deleted")
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
                    .message("Unable to delete shoppingList")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @DeleteMapping("/all/{id}")
    public ResponseEntity<Response> deleteAllShoppingList(
            @PathVariable("id") @Valid Long id
    ){
        ResponseEntity<Response> res;
        try {
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("shoppingList", shoppingListService.deleteAll(id)))
                    .message("All shoppingList deleted")
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
                    .message("Unable to delete shoppingList")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }
}
