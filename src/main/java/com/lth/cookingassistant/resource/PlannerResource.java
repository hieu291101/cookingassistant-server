package com.lth.cookingassistant.resource;

import com.lth.cookingassistant.exception.CANotFoundException;
import com.lth.cookingassistant.model.Planner;
import com.lth.cookingassistant.model.PlannerMealofday;
import com.lth.cookingassistant.request.PlannerRequest;
import com.lth.cookingassistant.response.PlannerResponse;
import com.lth.cookingassistant.response.Response;
import com.lth.cookingassistant.service.MealDetailService;
import com.lth.cookingassistant.service.PlannerService;
import com.lth.cookingassistant.utils.RecipeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/planner")
@PreAuthorize("hasRole('ROLE_USER')")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PlannerResource {

    @Autowired
    PlannerService plannerService;

    @Autowired
    MealDetailService mealDetailService;

    @Autowired
    RecipeUtils recipeUtils;

    @GetMapping("/{id}")
    public ResponseEntity<Response> getPlanner(@PathVariable Long id){
        ResponseEntity<Response> res;
        try {
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("planner", plannerService.get(id)))
                    .message("Planner retrieved")
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
                    .message("Unable to get planner")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createPlanner(@RequestBody @Valid PlannerRequest plannerRequest) {
        ResponseEntity<Response> res;

        try {
            Planner planner = plannerService.
                    addRecipeToPlanner(plannerRequest.getAccountId(),
                            plannerRequest.getRecipeId(),
                            plannerRequest.getDayOfWeek(),
                            plannerRequest.getMeal());
                        
            PlannerMealofday pmod = null;
            PlannerResponse plannerResponse = new PlannerResponse();

            if(planner != null) {
                pmod = planner.getPlannerMealofdays()
                    .get(planner.getPlannerMealofdays().size()-1);
                plannerResponse = PlannerResponse.builder()
                                    .plannerId(planner.getPlannerId())
                                    .dayOfWeek(planner.getDayOfWeek())
                                    .isDelete(planner.getIsDelete())
                                    .meal(pmod.getMeal())
                                    .mealDesciption(pmod.getMealDesciption())
                                    .recipe(mealDetailService.findMealDetailByPlannerMealOfDay(pmod).getRecipe())
                                    .build();
            }

            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("planner", plannerResponse))
                    .message("planner created")
                    .status(OK)
                    .statusCode(OK.value())
                    .build());
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to save planner")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deletePlanner(@PathVariable("id") @Valid Long id){
        ResponseEntity<Response> res;
        try {
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("planner", plannerService.delete(id)))
                    .message("Planner retrieved")
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
                    .message("Unable to delete planner")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<Response> terminatePlanner(@PathVariable Long id){
//
//        ResponseEntity<Response> rep;
//        try {
//            rep = ResponseEntity.ok(Response.builder()
//                    .timeStamp(now())
//                    .data(Map.of("planner", plannerService.update(ac)))
//                    .message("Account retrieved")
//                    .status(RESET_CONTENT)
//                    .statusCode(RESET_CONTENT.value())
//                    .build());
//
//        } catch (CANotFoundException cfe) {
//            throw cfe;
//        } catch (Exception e) {
//            e.printStackTrace();
//            rep = ResponseEntity.internalServerError().body(Response.builder()
//                    .timeStamp(now())
//                    .message("Unable to update account")
//                    .status(INTERNAL_SERVER_ERROR)
//                    .statusCode(INTERNAL_SERVER_ERROR.value())
//                    .build());
//        }
//        return rep;
//    }
}
