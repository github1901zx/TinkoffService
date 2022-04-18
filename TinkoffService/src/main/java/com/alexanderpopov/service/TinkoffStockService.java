package com.alexanderpopov.service;

import com.alexanderpopov.model.Stock;
import ru.tinkoff.invest.openapi.MarketContext;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrumentList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TinkoffStockService implements StockService{
    private final OpenApi openApi;
    @Override
    public Stock getStockByTicker(String ticker) {
        MarketContext marketContext = openApi.getMarketContext();
        CompletableFuture<MarketInstrumentList> marketInstrumentListCompletableFuture = marketContext.searchMarketInstrumentsByTicker(ticker);
        List<MarketInstrument> marketInstrumentList = marketInstrumentListCompletableFuture.join().getInstruments();
        return null;
    }
}
