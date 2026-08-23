package com.vacation_optimizer.service;

import com.vacation_optimizer.client.HolidayClient;
import com.vacation_optimizer.client.HolidayResponse;
import com.vacation_optimizer.dto.RequestDto;
import com.vacation_optimizer.dto.VacationOptionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationService {
    private final HolidayClient holidayClient;

    public List<VacationOptionDto> calculateBestVacation(RequestDto request) {
        List<HolidayResponse> listHolidays = holidayClient.getHolidays(request.getYear());
        System.out.println(listHolidays);
        List<HolidayResponse> holidaysInMonth = listHolidays.stream()
                .filter(feriado -> feriado.getDate().getMonth() == request.getMonth())
                .toList();
        List<LocalDate> datasFeriados = holidaysInMonth.stream()
                .map(HolidayResponse::getDate)
                .toList();

        List<VacationOptionDto> options = new ArrayList<>();
        LocalDate firstDayMonth = LocalDate.of(request.getYear(), request.getMonth(), 1);
        int lengthMonth = firstDayMonth.lengthOfMonth();
        for (int dia = 1; dia <= (lengthMonth - request.getVacationDays() + 1); dia++) {
            LocalDate dataInicioFerias = firstDayMonth.withDayOfMonth(dia);
            LocalDate dataFimFerias = dataInicioFerias.plusDays(request.getVacationDays() - 1);

            int extrasBefore = 0;
            LocalDate checkingDay = dataInicioFerias.minusDays(1);
            while (checkingDay.getDayOfWeek() == DayOfWeek.SATURDAY
                    || checkingDay.getDayOfWeek() == DayOfWeek.SUNDAY
                    || datasFeriados.contains(checkingDay)) {
                extrasBefore++;
                checkingDay = checkingDay.minusDays(1);
            }

            int extrasAfter = 0;
            LocalDate checkingDayAfter = dataFimFerias.plusDays(1);
            while (checkingDayAfter.getDayOfWeek() == DayOfWeek.SATURDAY
                    || checkingDayAfter.getDayOfWeek() == DayOfWeek.SUNDAY
                    || datasFeriados.contains(checkingDayAfter)) {
                extrasAfter++;
                checkingDayAfter = checkingDayAfter.plusDays(1);
            }

            VacationOptionDto option = VacationOptionDto.builder()
                    .startDate(dataInicioFerias)
                    .endDate(dataFimFerias)
                    .extraDaysGained(extrasBefore + extrasAfter)
                    .totalDaysOff(request.getVacationDays() + extrasBefore + extrasAfter)
                    .build();

            options.add(option);

        }
        List<VacationOptionDto> sortedOptions = options.stream()
                .sorted(Comparator.comparing(VacationOptionDto::getExtraDaysGained).reversed())
                .collect(Collectors.toList());
        return sortedOptions;
    }
}
