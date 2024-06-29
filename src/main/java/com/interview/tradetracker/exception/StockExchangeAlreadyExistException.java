package com.interview.tradetracker.exception;

public class StockExchangeAlreadyExistException extends Exception {

  public StockExchangeAlreadyExistException(String stockExchangeName) {
    super("Stock exchange already exists: " + stockExchangeName);
  }
}
