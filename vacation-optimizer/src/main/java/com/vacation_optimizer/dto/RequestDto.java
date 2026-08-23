package com.vacation_optimizer.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Month;

@Getter @Setter
public class RequestDto {
    private int vacationDays;
    private Month month;
    private int year;
}
