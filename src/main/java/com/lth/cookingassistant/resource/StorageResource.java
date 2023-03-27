package com.lth.cookingassistant.resource;

import com.lth.cookingassistant.exception.CANotFoundException;
import com.lth.cookingassistant.model.Storage;
import com.lth.cookingassistant.request.StorageRequest;
import com.lth.cookingassistant.response.Response;
import com.lth.cookingassistant.response.StorageResponse;
import com.lth.cookingassistant.service.StorageService;
import com.lth.cookingassistant.utils.StorageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/storage")
@CrossOrigin(origins = "*", allowedHeaders = "*")
// @PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class StorageResource {
    private final StorageService storageService;

    private final StorageUtils util;

    @PostMapping("/{id}")
    public ResponseEntity<Response> getALlStorage(@PathVariable Long id){
        ResponseEntity<Response> res;
        try{
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("storage", storageService.list(30, id)
                            .stream().map(storage -> StorageResponse.builder()
                                    .ingredientName(storage.getId().getIngredientName())
                                    .bestBefore(storage.getBestBefore())
                                    .measure(storage.getMeasure())
                                    .quantity(storage.getQuantity())
                                    .build())
                    ))
                    .message("storage retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to get storage")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @PostMapping("/pagination/{id}")
    public ResponseEntity<Response> getStorage(@RequestParam(value = "page", required = true) Integer page, @PathVariable Long id){
        ResponseEntity<Response> res;
        try{
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("storage", storageService.list(page, 30, id)
                            .stream().map(storage -> StorageResponse.builder()
                                    .ingredientName(storage.getId().getIngredientName())
                                    .bestBefore(storage.getBestBefore())
                                    .measure(storage.getMeasure())
                                    .quantity(storage.getQuantity())
                                    .build())
                    ))
                    .message("storage retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to get storage")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @PostMapping("/add")
    public ResponseEntity<Response> saveStorage(@RequestBody @Valid StorageRequest storageRequest){
        ResponseEntity<Response> res;
        try {
            Storage storage = new Storage();
            util.copyValuesFromRequest(storageRequest, storage);
        
            storage = storageService.create(storage);
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("storage",
                            StorageResponse.builder()
                                    .ingredientName(storage.getId().getIngredientName())
                                    .bestBefore(storage.getBestBefore())
                                    .measure(storage.getMeasure())
                                    .quantity(storage.getQuantity())
                                    .build()
                    ))
                    .message("Storage created")
                    .status(OK)
                    .statusCode(OK.value())
                    .build());
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to save storage")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Response> deleteStorage(
            @PathVariable("id") @Valid Long id,
            @RequestParam(value = "ingredient_name") String ingredientName
            ){
        ResponseEntity<Response> res;
        try {
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("storage", storageService.delete(id, ingredientName)))
                    .message("Storage deleted")
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
                    .message("Unable to delete storage")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateStorage(
        @PathVariable Long id, 
        @RequestParam(value = "ingredient_name", required = true) String ingredientName, 
        @RequestBody @Valid StorageRequest storageRequest
        ){
        ResponseEntity<Response> res;
        try {
            Storage storage = storageService.get(id, ingredientName);
            util.copyValuesFromRequest(storageRequest, storage);
            storage = storageService.update(storage);
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("storage",
                            StorageResponse.builder()
                                    .ingredientName(storage.getId().getIngredientName())
                                    .bestBefore(storage.getBestBefore())
                                    .measure(storage.getMeasure())
                                    .quantity(storage.getQuantity())
                                    .build()
                    ))
                    .message("storage updated successfully")
					.status(RESET_CONTENT)
					.statusCode(RESET_CONTENT.value())
					.build());
        } catch (CANotFoundException cfe) {
            throw cfe;
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to update storage")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }
}
