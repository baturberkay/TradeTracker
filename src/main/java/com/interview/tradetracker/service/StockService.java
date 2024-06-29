package com.interview.tradetracker.service;

import com.interview.tradetracker.entity.Stock;
import com.interview.tradetracker.exception.StockAlreadyExistException;
import com.interview.tradetracker.exception.StockNotFoundException;
import com.interview.tradetracker.request.CreateStockRequest;

public interface StockService {

  void create(CreateStockRequest createStockRequest) throws StockAlreadyExistException;

  void updatePrice(String name, Double newPrice) throws StockNotFoundException;

  void delete(String name) throws StockNotFoundException;

  Stock getStockByName(String name) throws StockNotFoundException;

}
