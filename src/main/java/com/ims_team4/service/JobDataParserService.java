package com.ims_team4.service;

import java.time.LocalDate;
import java.util.Set;

public interface JobDataParserService {
    Long parseSalary(String salaryStr);
    LocalDate parseExcelDate(String excelDateStr);

    Set<String> parseList(String input);
}
