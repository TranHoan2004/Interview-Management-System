package com.ims_team4.utils.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ImportExcelFile {
    private static final Logger LOGGER = Logger.getLogger(ImportExcelFile.class.getName());

    public List<List<String>> readExcelFile(InputStream inputStream) throws IOException {
        List<List<String>> data = new ArrayList<>();
        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên
            for (Row row : sheet) {
                List<String> rowData = getStrings(row);
                data.add(rowData);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi đọc file Excel", e);
            throw e;
        }
        return data;
    }

    private List<String> getStrings(Row row) {
        List<String> rowData = new ArrayList<>();
        for (Cell cell : row) {
            switch (cell.getCellType()) {
                case STRING -> rowData.add(cell.getStringCellValue());
                case NUMERIC -> rowData.add(String.valueOf(cell.getNumericCellValue()));
                case BOOLEAN -> rowData.add(String.valueOf(cell.getBooleanCellValue()));
                case FORMULA -> rowData.add(cell.getCellFormula());
                default -> rowData.add("");
            }
        }
        return rowData;
    }

    public List<List<String>> getData(MultipartFile file) throws IOException {
        return readExcelFile(file.getInputStream());
    }
}
