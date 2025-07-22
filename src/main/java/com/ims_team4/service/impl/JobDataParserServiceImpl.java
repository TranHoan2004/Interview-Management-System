package com.ims_team4.service.impl;

import com.ims_team4.service.JobDataParserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JobDataParserServiceImpl implements JobDataParserService {

    private double parseDouble(String value, String errorMessage) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage + value);
        }
    }

    @Override
    public Long parseSalary(String salaryStr) {
        return (long) parseDouble(salaryStr, "Invalid salary value: ");
    }

    @Override
    public LocalDate parseExcelDate(String excelDateStr) {
        double excelDate = parseDouble(excelDateStr, "Invalid Excel serial date format: ");
        if (excelDate > 0) {
            long days = (long) excelDate;
            return LocalDate.of(1900, 1, 1).plusDays(days - 2); // Excel tính 1900 là năm nhuận, cần trừ đi 2 ngày
        } else {
            throw new IllegalArgumentException("Invalid Excel serial date: " + excelDateStr);
        }
    }

    @Override
    public Set<String> parseList(String input) {
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());
    }
}
