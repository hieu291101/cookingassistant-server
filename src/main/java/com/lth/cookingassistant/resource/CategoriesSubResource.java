package com.lth.cookingassistant.resource;

import com.lth.cookingassistant.response.Response;
import com.lth.cookingassistant.service.CategoriesMainService;
import com.lth.cookingassistant.service.CategoriesSubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin("http://localhost:3001")
@RequestMapping("/api/categoriessub")
@RequiredArgsConstructor
public class CategoriesSubResource {
    private final CategoriesSubService categoriesSubService;

    @GetMapping("/getCategoriesSubBy/{cateMainId}")
    public ResponseEntity<Response> getCategoriesSubBy(@PathVariable int cateMainId){
        ResponseEntity<Response> res;
        try{
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("categories sub", categoriesSubService.searchCategoriesSub(cateMainId)))
                    .message("Categories sub retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to get categories sub")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }
}
