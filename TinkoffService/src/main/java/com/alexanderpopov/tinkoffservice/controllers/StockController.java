package com.alexanderpopov.tinkoffservice.controllers;

import com.alexanderpopov.model.Stock;
import org.springframework.web.bind.annotation.GetMapping;

public class StockController {
    private final StockService stockService;

    @GetMapping("/stocks/{ticker}")
    public Stock getStock(String ticker){
        stockService.getStockByTicker(ticker);
    }
}
