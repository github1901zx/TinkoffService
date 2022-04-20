package com.alexanderpopov.tinkoffservice.service;

import com.alexanderpopov.tinkoffservice.model.Stock;

public interface StockService{
    Stock getStockByTicker(String ticker);
}
