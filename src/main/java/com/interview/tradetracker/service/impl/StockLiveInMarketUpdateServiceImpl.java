package com.interview.tradetracker.service.impl;

import static com.interview.tradetracker.util.StockUtils.isLiveInMarket;

import com.interview.tradetracker.entity.Stock;
import com.interview.tradetracker.entity.StockExchange;
import com.interview.tradetracker.repository.StockExchangeRepository;
import com.interview.tradetracker.service.StockLiveInMarketUpdateService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StockLiveInMarketUpdateServiceImpl implements StockLiveInMarketUpdateService {

  private final StockExchangeRepository stockExchangeRepository;

  public StockLiveInMarketUpdateServiceImpl(StockExchangeRepository stockExchangeRepository) {
    this.stockExchangeRepository = stockExchangeRepository;
  }

  @Override
  public void updateLiveInMarketByStocksOnDelete(List<Stock> stockList) {
    Optional<List<StockExchange>> stockExchangeListOptional =
        stockExchangeRepository.findStockExchangeByStocks(stockList);

    stockExchangeListOptional.ifPresent(stockExchanges -> stockExchanges
        .forEach(stockExchange -> {
          stockList.forEach(stockExchange.getStocks()::remove);
          stockExchange.setLiveInMarket(isLiveInMarket(stockExchange.getStocks()));
          stockExchangeRepository.save(stockExchange);
        }));
  }
}
