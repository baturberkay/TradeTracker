package com.interview.tradetracker.exception;

public class StockNotFoundException extends Exception {

  public StockNotFoundException(String stockName) {
    super("Stock not found: " + stockName);
  }
}
