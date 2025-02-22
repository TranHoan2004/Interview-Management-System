package com.ims_team4.utils.excel;

import jakarta.servlet.http.Part;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImportExcelFile {
    @NotNull
    private List<List<String>> readExcelFile(InputStream inputStream) throws IOException {
        List<List<String>> data = new ArrayList<>();
        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(0); // get the first sheet
            for (Row row : sheet) {
                List<String> rowData = getStrings(row);
                data.add(rowData);
            }
        } catch (Exception e) {
            Logger.getLogger(ImportExcelFile.class.getName()).log(Level.ALL, e.getMessage(), e);
            throw e;
        }
        return data;
    }

    @NotNull
    private List<String> getStrings(@NotNull Row row) {
        List<String> rowData = new ArrayList<>();
        for (Cell cell : row) {
            // read for each cell
            switch (cell.getCellType()) {
                case STRING -> rowData.add(cell.getStringCellValue());
                case NUMERIC -> rowData.add(String.valueOf(cell.getNumericCellValue()));
                case BOOLEAN -> rowData.add(String.valueOf(cell.getBooleanCellValue()));
                case FORMULA -> rowData.add(String.valueOf(cell.getCellFormula()));
                default -> rowData.add("");
            }
        }
        return rowData;
    }

    @NotNull
    @Bean
    public List<List<String>> getData(@NotNull Part part) throws Exception {
        InputStream fileContent = part.getInputStream();
        return readExcelFile(fileContent);
    }
}
