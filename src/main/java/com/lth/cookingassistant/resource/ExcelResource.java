package com.lth.cookingassistant.resource;

import com.lth.cookingassistant.service.ExcelService;
import com.lth.cookingassistant.service.ReportService;
import com.lth.cookingassistant.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/excel/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ExcelResource {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ShoppingListService shoppingListService;

    @Autowired
    ExcelService fileService;

    @GetMapping("/export/{id}")
    public ResponseEntity<Resource> exportToExcel(@PathVariable Long id) throws Exception {
        String filename = "shopppinglist.xlsx";
        InputStreamResource file = new InputStreamResource(fileService.load(id));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}
