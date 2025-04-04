package com.ims_team4.service;

import org.springframework.web.multipart.MultipartFile;

public interface JobExcelImporterService {
    void importJobsFromExcel(Long managerId, MultipartFile file);
}
