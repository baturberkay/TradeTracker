package com.interview.tradetracker.exception;

public class StockExchangeNotFoundException extends Exception {

  public StockExchangeNotFoundException(String stockExchangeName) {
    super("Stock exchange not found: " + stockExchangeName);
  }
}
