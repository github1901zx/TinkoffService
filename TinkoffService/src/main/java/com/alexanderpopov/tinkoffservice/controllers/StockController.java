package com.alexanderpopov.tinkoffservice.controllers;

import com.alexanderpopov.tinkoffservice.dto.StockDto;
import com.alexanderpopov.tinkoffservice.dto.TickerDto;
import com.alexanderpopov.tinkoffservice.model.FigiesDto;
import com.alexanderpopov.tinkoffservice.model.Stock;
import com.alexanderpopov.tinkoffservice.model.StocksPriceDto;
import com.alexanderpopov.tinkoffservice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @GetMapping("/stocks/{ticker}")
    public Stock getStock(@PathVariable String ticker){
        return stockService.getStockByTicker(ticker);
    }

    @PostMapping("/stocks/getStockByTicker")
    public StockDto getStockByTickers(@RequestBody TickerDto tickerDto){
        return stockService.getStockByTickers(tickerDto);
    }

    @PostMapping("/price")
    public StocksPriceDto getPrices(@RequestBody FigiesDto figiesDto){
    return stockService.getPrices(figiesDto);
    }
}
