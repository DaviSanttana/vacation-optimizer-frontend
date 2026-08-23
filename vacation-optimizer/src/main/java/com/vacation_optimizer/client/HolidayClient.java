package com.vacation_optimizer.client;


import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class HolidayClient {
    private final RestClient restClient;

    public HolidayClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("https://brasilapi.com.br/api/feriados/v1")
                .build();
    }

    public List<HolidayResponse> getHolidays(int year) {
        return this.restClient.get()
                .uri("/{year}", year)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<HolidayResponse>>() {});
    }
}
