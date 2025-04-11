package com.ims_team4.service;

import com.ims_team4.model.Users;
import org.springframework.web.multipart.MultipartFile;

public interface JobExcelImporterService {
    void importJobsFromExcel(Long managerId, MultipartFile file);

    void importJobsFromExcelForAdmin(Users admin, MultipartFile file);
}
