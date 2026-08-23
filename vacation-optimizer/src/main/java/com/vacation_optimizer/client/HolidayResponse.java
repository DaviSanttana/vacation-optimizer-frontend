package com.vacation_optimizer.client;

import lombok.Data;
import java.time.LocalDate;

@Data
public class HolidayResponse {
    private LocalDate date;
    private String type;
    private String name;
    private String fullName;
}
