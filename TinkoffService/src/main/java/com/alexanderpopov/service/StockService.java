package com.alexanderpopov.service;

import com.alexanderpopov.model.Stock;

public interface StockService{
    Stock getStockByTicker(String ticker);
}
