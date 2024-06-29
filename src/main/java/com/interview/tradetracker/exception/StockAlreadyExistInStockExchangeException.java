package com.interview.tradetracker.exception;

public class StockAlreadyExistException extends Exception {

  public StockAlreadyExistException(String stockName) {
    super("Stock already exists: " + stockName);
  }
}
