package com.ims_team4.dto;

import java.util.Date;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ChatDetailDTO {
    private long chatDetailId;
    private long ChatID;
    private String Message;
    private Date Timestamp;
    private String Sender;
}
