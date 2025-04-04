package com.ims_team4.utils.excel;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// Duc Long
public class ExportExcelFile<T> {
    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private final List<T> listData;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public ExportExcelFile(List<T> listData) {
        this.listData = listData;
        workbook = new XSSFWorkbook();
    }

    public ExportExcelFile<T> writeHeaderLine(@NotNull String[] headers) {
        sheet = workbook.createSheet("data export");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();

        font.setBold(true);
        font.setFontHeight(12);
        font.setFontName("Times New Roman");
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);

        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        for (int i = 0; i < headers.length; i++) {
            createCell(row, i, headers[i], style);
        }

        return this;
    }

    private void createCell(@NotNull Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer i) {
            cell.setCellValue(i);
        } else if (value instanceof Boolean b) {
            cell.setCellValue(b);
        } else if (value instanceof Long l) {
            cell.setCellValue(l);
        } else if (value instanceof LocalDate localDate) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            cell.setCellValue(localDate.format(formatter));
        } else if (value instanceof LocalDateTime localDateTime) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            cell.setCellValue(localDateTime.format(formatter));
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    public ExportExcelFile<T> writeDataLines(String[] fields, Class<?> clazz) {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();

        style.setFont(font);
        font.setFontHeight(12);
        font.setFontName("Times New Roman");
        font.setBold(true);

        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        for (var data : this.listData) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            for (String fieldName : fields) {
                try {
                    Field field = clazz.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    Object value = field.get(data);
                    createCell(row, columnCount, value, style);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    logger.log(Level.SEVERE, e.getMessage(), e);
                }
                columnCount++;
            }
        }
        return this;
    }

    public void export(@NotNull HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
