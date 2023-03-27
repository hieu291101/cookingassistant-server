package com.lth.cookingassistant.service.impl;

import com.lth.cookingassistant.model.ShoppingList;
import com.lth.cookingassistant.service.ReportService;
import com.lth.cookingassistant.service.ShoppingListService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {
    private final ShoppingListService shoppingListService;

    @Override
    public void generateExcel(long account_id) throws IOException {
        Collection<ShoppingList> list = shoppingListService.list(30, account_id);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ingredient Info");
        Row row = sheet.createRow(0);

        row.createCell(0).setCellValue("Ingredient");
        row.createCell(1).setCellValue("Quantity");
        row.createCell(2).setCellValue("Measure");
        row.createCell(3).setCellValue("Note");

        int dataRowIndex = 1;
        for (ShoppingList sl : list) {
            Row dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(sl.getId().getIngredientName());
            dataRow.createCell(1).setCellValue(sl.getQuantity());
            dataRow.createCell(2).setCellValue(sl.getMeasure());
            dataRow.createCell(3).setCellValue(sl.getNote());
            dataRowIndex++;
        }

        // Write workbook to output file
        try (FileOutputStream outputStream = new FileOutputStream("recipe")) {
            workbook.write(outputStream);
        }
    }
}