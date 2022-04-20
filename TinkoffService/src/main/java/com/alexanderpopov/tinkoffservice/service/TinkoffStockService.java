package com.alexanderpopov.tinkoffservice.service;

import com.alexanderpopov.tinkoffservice.exception.StockNotFoundException;
import com.alexanderpopov.tinkoffservice.model.Currency;
import com.alexanderpopov.tinkoffservice.model.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.invest.openapi.MarketContext;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrumentList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class TinkoffStockService implements StockService{

    private final OpenApi openApi;

    @Override
    public Stock getStockByTicker(String ticker) {
        MarketContext marketContext = openApi.getMarketContext();
        CompletableFuture<MarketInstrumentList> marketInstrumentListCompletableFuture = marketContext.searchMarketInstrumentsByTicker(ticker);
        List<MarketInstrument> marketInstrumentList = marketInstrumentListCompletableFuture.join().getInstruments();
        if(marketInstrumentList.isEmpty()){
            throw new StockNotFoundException(String.format("Stock %S not found",ticker));
        }
        MarketInstrument marketInstrument = marketInstrumentList.get(0);
        return new Stock(
                marketInstrument.getTicker(),
                marketInstrument.getFigi(),
                marketInstrument.getName(),
                marketInstrument.getType().getValue(),
                Currency.valueOf(marketInstrument.getCurrency().getValue()),
                "Tinkoff"
        );
    }
}
