package com.ims_team4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DashboardStatsDTO {

    private int totalJob;
    private int openJob;
    private int closeJob;
    private double successRate;
    private int totalJobChange;
    private int openJobChange;
    private int closeJobChange;
    private int successRateChange;

}
