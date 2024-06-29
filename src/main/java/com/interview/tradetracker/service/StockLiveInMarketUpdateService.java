package com.interview.tradetracker.service;

import com.interview.tradetracker.entity.Stock;
import java.util.List;

public interface StockLiveInMarketUpdateService {

  void updateLiveInMarketByStocksOnDelete(List<Stock> stockList);

}
