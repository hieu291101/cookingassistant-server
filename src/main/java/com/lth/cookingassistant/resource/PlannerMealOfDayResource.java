package com.lth.cookingassistant.resource;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lth.cookingassistant.exception.CANotFoundException; 
import com.lth.cookingassistant.model.PlannerMealofday;
import com.lth.cookingassistant.request.DeletePlannerRequest;
import com.lth.cookingassistant.request.PlannerMealRequest;
import com.lth.cookingassistant.response.PlannerMealResponse;
import com.lth.cookingassistant.response.Response;
import com.lth.cookingassistant.service.PlannerMealOfDayService;

import lombok.extern.slf4j.Slf4j;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/plannermeal")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class PlannerMealOfDayResource {
    @Autowired
    PlannerMealOfDayService plannerMealOfDayService;

    @GetMapping("/all/{accountId}")
    public ResponseEntity<Response> getAllPlanner(
        @PathVariable Long accountId
    ){
        ResponseEntity<Response> res;   
        try {
            List<PlannerMealofday> pms = plannerMealOfDayService.getAll(accountId);
            
            log.info("{}", plannerMealOfDayService.getAll(accountId).size());
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("plannermeal",
                        pms.stream().map(pm -> PlannerMealResponse.builder()
                                                    .plannerId(pm.getPlanner().getPlannerId())
                                                    .dayOfWeek(pm.getPlanner().getDayOfWeek())
                                                    .isDelete(pm.getIsDelete())
                                                    .meal(pm.getMeal())
                                                    .mealDesciption(pm.getMealDesciption())
                                                    .recipe(pm.getMealDetails().get(0).getRecipe())
                                                    .mealOfDayId(pm.getMealofdayId())
                                                    .build())
                        ))
                    .message("Planner meal retrieved")
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
                    .message("Unable to get planner meal")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    } 

    @PostMapping()
    public ResponseEntity<Response> getPlanner(
        @RequestBody PlannerMealRequest plannerMealRequest
    ){
        ResponseEntity<Response> res;   
        try {
            List<PlannerMealofday> pms = plannerMealOfDayService.get(plannerMealRequest);
            
            log.info("{}", plannerMealOfDayService.get(plannerMealRequest).size());
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("plannermeal",
                        pms.stream().map(pm -> PlannerMealResponse.builder()
                                                    .plannerId(pm.getPlanner().getPlannerId())
                                                    .dayOfWeek(pm.getPlanner().getDayOfWeek())
                                                    .isDelete(pm.getIsDelete())
                                                    .meal(pm.getMeal())
                                                    .mealDesciption(pm.getMealDesciption())
                                                    .recipe(pm.getMealDetails().get(0).getRecipe())
                                                    .mealOfDayId(pm.getMealofdayId())
                                                    .build())
                        ))
                    .message("Planner meal retrieved")
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
                    .message("Unable to get planner meal")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Response> deletePlannerMeal(@RequestBody DeletePlannerRequest deletePlannerRequest){
        ResponseEntity<Response> res;
        try {
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("plannermeal", plannerMealOfDayService.deleteByAccountAndRecipe(deletePlannerRequest)))
                    .message("Planner meal deleted")
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
                    .message("Unable to delete planner meal")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }
}
