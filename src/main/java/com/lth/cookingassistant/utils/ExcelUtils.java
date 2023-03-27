package com.lth.cookingassistant.utils;

import com.lth.cookingassistant.model.ShoppingList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;

public class ExcelUtils {

    public static ByteArrayInputStream shoppinglistToExcel(Collection<ShoppingList> list) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()){
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

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    
}
