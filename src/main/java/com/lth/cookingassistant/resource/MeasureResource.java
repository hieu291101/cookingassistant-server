package com.lth.cookingassistant.resource;

import com.lth.cookingassistant.exception.CANotFoundException;
import com.lth.cookingassistant.model.Measure;
import com.lth.cookingassistant.response.PaginationResponse;
import com.lth.cookingassistant.response.Response;
import com.lth.cookingassistant.service.MeasureService;
import com.lth.cookingassistant.utils.MeasureUtils;

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
@RequestMapping("/api/measure")
@RequiredArgsConstructor
public class  MeasureResource {
    private final MeasureService measureService;

    private final MeasureUtils util;

    @GetMapping("/getAll")
    public ResponseEntity<Response> getALlMeasure(){
        ResponseEntity<Response> res;
        try{
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("measure", measureService.list(30)))
                    .message("Measure retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to get measure")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @GetMapping("/pagination")
    public ResponseEntity<Response> getMeasures(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "page", required = true) Integer page
    ){
        ResponseEntity<Response> res;
        try{
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("measure", measureService.list(page, limit)))
                    .pagination(
                            PaginationResponse
                                    .builder()
                                    .page(page)
                                    .limit(limit)
                                    .totalRows(measureService.countMeasure())
                                    .build()
                    )
                    .message("measure retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to get measure")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }


    @GetMapping("/getMeasureBy")
    public ResponseEntity<Response> getMeasureBy(@RequestParam(value = "keyword", required = true) String keyword ){
        ResponseEntity<Response> res;
        try{
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("measure", measureService.searchMeasure(keyword)))
                    .message("Measure retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to get measure")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @PostMapping ("/create")
    public ResponseEntity<Response> saveMeasure(@RequestBody @Valid Measure measure){
        ResponseEntity<Response> res;
        try {
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("measure", measureService.create(measure)))
                    .message("measure created")
                    .status(OK)
                    .statusCode(OK.value())
                    .build());
        } catch(Exception e) {
            e.printStackTrace();
            res = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to save measure")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Response> updateMeasure(@PathVariable int id, @RequestBody Measure measure){

        ResponseEntity<Response> rep;
        try {
            //db Object
            Measure ms = measureService.get(id);
            //copy non-null values from request to Database object
            util.copyNonNullValues(measure, ms);
            
            rep = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("measure", measureService.update(ms)))
                    .message("measure retrieved")
                    .status(RESET_CONTENT)
                    .statusCode(RESET_CONTENT.value())
                    .build());

        } catch (CANotFoundException cfe) {
            throw cfe;
        } catch (Exception e) {
            e.printStackTrace();
            rep = ResponseEntity.internalServerError().body(Response.builder()
                    .timeStamp(now())
                    .message("Unable to update measure")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return rep;
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<Response> deleteMeasure(@PathVariable("id") @Valid int id){
        ResponseEntity<Response> res;
        try {
            res = ResponseEntity.ok(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("measure", measureService.delete(id)))
                    .message("measure retrieved")
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
                    .message("Unable to delete measure")
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
        return res;
    }
}
