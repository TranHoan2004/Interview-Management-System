package com.ims_team4.dto;

import com.ims_team4.model.Interview;
import com.ims_team4.model.Skill;
import com.ims_team4.model.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;
// Duc Long
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CandidateDTO {
    private Long id;
    private long highestEducation;
    private int experience;
    private int positionId;
    private String positionName;
    private byte[] cv;
    private Long offerId;
    //Hai Dang
    private Long userId;
    private String fullName; // ✅ Thêm tên ứng viên
    private String email; // ✅ Thêm email
    private String phone; // ✅ Thêm số điện thoại
    private String ownerHR; // ✅ Thêm người phụ trách HR
    private String status; // ✅ Thêm trạng thái ứng viên
    private Set<String> skills; // ✅ Thêm danh sách kỹ năng (nếu cần)
    private LocalDate dob;
    private String address;
    private Integer gender;
    private String recruiter;
    private String note;


    // Getter cho cvLink (trả về link giả định nếu có)
    public String getCvLink() {
        return (cv != null) ? "/cvs/" + id : null;
    }

}
