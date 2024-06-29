package com.interview.tradetracker.service;

import com.interview.tradetracker.entity.StockExchange;
import com.interview.tradetracker.exception.StockAlreadyExistInStockExchangeException;
import com.interview.tradetracker.exception.StockExchangeAlreadyExistException;
import com.interview.tradetracker.exception.StockExchangeNotFoundException;
import com.interview.tradetracker.exception.StockNotFoundException;
import com.interview.tradetracker.request.CreateStockExchangeRequest;

public interface StockExchangeService {
  void create(CreateStockExchangeRequest createStockExchangeRequest) throws StockExchangeAlreadyExistException;

  StockExchange getStocks(String stockExchangeName) throws StockExchangeNotFoundException;

  void addStock(String stockExchangeName, String stockName)
      throws StockExchangeNotFoundException, StockNotFoundException, StockAlreadyExistInStockExchangeException;

  void deleteStock(String stockExchangeName, String stockName)
      throws StockExchangeNotFoundException, StockNotFoundException;
}
