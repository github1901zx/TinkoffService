package com.alexanderpopov.tinkoffservice.dto;

import com.alexanderpopov.tinkoffservice.model.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockDto {
    List<Stock> stocks;
}
