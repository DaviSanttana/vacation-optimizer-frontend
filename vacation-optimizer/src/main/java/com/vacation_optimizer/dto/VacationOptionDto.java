package com.vacation_optimizer.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class VacationOptionDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private int extraDaysGained;
    private int totalDaysOff;
}
