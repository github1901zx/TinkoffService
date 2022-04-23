package com.alexanderpopov.tinkoffservice.model;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@AllArgsConstructor
@Value
public class StocksPriceDto {
    private List<StockPrice> priceList;
}
