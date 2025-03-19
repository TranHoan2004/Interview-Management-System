package com.ims_team4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class NotificationDTO {
    private Long id;
    private Long userID;
    private String title;
    private String link;
    private String message;
    private Boolean status;
}
