package com.alexanderpopov.tinkoffservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TickerDto {
    private List<String> tickers;
}
