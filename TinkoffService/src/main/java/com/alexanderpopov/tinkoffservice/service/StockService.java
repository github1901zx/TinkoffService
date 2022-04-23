package com.alexanderpopov.tinkoffservice.service;

import com.alexanderpopov.tinkoffservice.dto.StockDto;
import com.alexanderpopov.tinkoffservice.dto.TickerDto;
import com.alexanderpopov.tinkoffservice.model.FigiesDto;
import com.alexanderpopov.tinkoffservice.model.Stock;
import com.alexanderpopov.tinkoffservice.model.StocksPriceDto;

public interface StockService{
    Stock getStockByTicker(String ticker);
    StockDto getStockByTickers (TickerDto tickerDto);
    StocksPriceDto getPrices(FigiesDto figiesDto);
}
