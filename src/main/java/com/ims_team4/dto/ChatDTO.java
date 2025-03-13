package com.ims_team4.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ChatDTO {
    private long chatId;
    private long recuiterId;
    private long managerId;
}
