package com.alexanderpopov.tinkoffservice.service;

import com.alexanderpopov.tinkoffservice.dto.StockDto;
import com.alexanderpopov.tinkoffservice.dto.TickerDto;
import com.alexanderpopov.tinkoffservice.exception.StockNotFoundException;
import com.alexanderpopov.tinkoffservice.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.tinkoff.invest.openapi.MarketContext;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrumentList;
import ru.tinkoff.invest.openapi.model.rest.Orderbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TinkoffStockService implements StockService{

    private final OpenApi openApi;

    @Async
    public CompletableFuture<MarketInstrumentList> getMarketInstrumentTicker(String ticker){
        MarketContext marketContext = openApi.getMarketContext();
        return marketContext.searchMarketInstrumentsByTicker(ticker);
    }

    @Override
    public Stock getStockByTicker(String ticker) {
        CompletableFuture<MarketInstrumentList> marketInstrumentListCompletableFuture = getMarketInstrumentTicker(ticker);
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

    @Override
    public StockDto getStockByTickers (TickerDto tickersDto){
        List<CompletableFuture<MarketInstrumentList>> marketInstruments = new ArrayList<>();
        tickersDto.getTickers().forEach(ticker -> marketInstruments.add(getMarketInstrumentTicker(ticker)));
        List<Stock> stocks = marketInstruments.stream()
                .map(CompletableFuture::join)
                .map(m ->{
                    if(m.getInstruments().isEmpty()){
                        return m.getInstruments().get(0);
                    }
                    return null;
                })
                .filter(element -> Objects.nonNull(element))
                .map(m -> new Stock(
                        m.getTicker(),
                        m.getFigi(),
                        m.getName(),
                        m.getType().getValue(),
                        Currency.valueOf(m.getCurrency().getValue()),
                        "Tinkoff"
                ))
                .collect(Collectors.toList());

        return new StockDto(stocks);
    }

    public StockPrice getPrice(String figi){
        Orderbook orderbook = openApi.getMarketContext().getMarketOrderbook(figi, 0).join().get();
        return new StockPrice(figi, orderbook.getLastPrice().doubleValue());
    }

    @Override
    public StocksPriceDto getPrices(FigiesDto figiesDto){
        List<StockPrice> priceList = figiesDto.getFigies().stream()
                .map(this::getPrice)
                .collect(Collectors.toList());
        return new StocksPriceDto(priceList);
    }
}
