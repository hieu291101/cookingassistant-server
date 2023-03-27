package com.lth.cookingassistant.resource;

import com.lth.cookingassistant.response.Response;
import com.lth.cookingassistant.service.CategoriesMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin("http://localhost:3001")
@RequestMapping("/api/categoriesmain")
@RequiredArgsConstructor
public class CategoriesMainResource {
    private final CategoriesMainService categoriesMainService;

    @GetMapping("/getAll")
    public ResponseEntity<Response> getAllCategoriesMain( ){
        ResponseEntity<Response> res;
        try{
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("categories main", categoriesMainService.list(20)))
                    .message("Categories main retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to get categories main")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @GetMapping("/getCategoriesMainBy")
    public ResponseEntity<Response> getCategoriesMainBy(@RequestParam(value = "keyword", required = true) String keyword ){
        ResponseEntity<Response> res;
        try{
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("categories main", categoriesMainService.searchCategories(keyword)))
                    .message("Categories main retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to get categories main")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }
}
