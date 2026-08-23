package com.vacation_optimizer.controller;

import com.vacation_optimizer.dto.RequestDto;
import com.vacation_optimizer.dto.VacationOptionDto;
import com.vacation_optimizer.service.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vacations")
public class VacationController {
    private final VacationService vacationService;

    @PostMapping
    public List<VacationOptionDto> calculateBestVacation(@RequestBody RequestDto request){
        return vacationService.calculateBestVacation(request);
    }
}
