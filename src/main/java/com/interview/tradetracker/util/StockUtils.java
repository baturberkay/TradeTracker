package com.interview.tradetracker.util;

import com.interview.tradetracker.entity.Stock;
import java.util.Set;

public class StockUtils {

  private static final int LIVE_IN_MARKET_STOCK_THRESHOLD = 5;


  public static boolean isLiveInMarket(Set<Stock> stocks) {
    return stocks.size() >= LIVE_IN_MARKET_STOCK_THRESHOLD;
  }

}
