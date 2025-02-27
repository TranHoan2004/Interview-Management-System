package com.ims_team4.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BenefitDTO {
    private Long id;
    private String name;
}
