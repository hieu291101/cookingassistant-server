package com.lth.cookingassistant.resource;

import com.lth.cookingassistant.response.Response;
import com.lth.cookingassistant.service.CuisineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin("http://localhost:3001")
@RequestMapping("/api/cuisine")
@RequiredArgsConstructor
public class CuisineResource {
    private final CuisineService cuisineService;

    @GetMapping("/getCuisineBy")
    public ResponseEntity<Response> getMeasureBy(@RequestParam(value = "keyword", required = true) String keyword ){
        ResponseEntity<Response> res;
        try{
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("cuisine", cuisineService.searchCuisine(keyword)))
                    .message("Cuisine retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to get cuisine")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }
}
