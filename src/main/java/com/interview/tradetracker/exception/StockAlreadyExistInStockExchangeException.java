package com.interview.tradetracker.exception;

public class StockAlreadyExistInStockExchangeException extends Exception {

  public StockAlreadyExistInStockExchangeException(String stockName, String stockExchangeName) {
    super("Stock " + stockName +" already exist in stock exchange: " + stockExchangeName);
  }
}
