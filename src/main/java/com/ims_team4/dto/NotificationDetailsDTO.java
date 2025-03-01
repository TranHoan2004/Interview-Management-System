package com.ims_team4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class NotificationDetailsDTO {
    private Long id;
    private String link;
    private String message;
    private boolean status;
    private Long notificationID;
}
