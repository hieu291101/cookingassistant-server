package com.lth.cookingassistant.resource;

import com.lth.cookingassistant.exception.CANotFoundException;
import com.lth.cookingassistant.request.MealDetailRequest;
import com.lth.cookingassistant.response.Response;
import com.lth.cookingassistant.service.MealDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/mealdetail")
@RequiredArgsConstructor
public class MealDetailResource {
    private final MealDetailService mealDetailService;

    @DeleteMapping("/delete")
    public ResponseEntity<Response> deleteMealDetail(@RequestBody MealDetailRequest mealDetailRequest){
        ResponseEntity<Response> res;
        try {
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("account",
                            mealDetailService.delete(
                            mealDetailRequest.getRecipeId(),
                            mealDetailRequest.getMealOfDayId())
                            )
                    )
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
}
