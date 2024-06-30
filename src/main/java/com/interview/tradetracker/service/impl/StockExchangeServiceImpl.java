package com.interview.tradetracker.service.impl;

import static com.interview.tradetracker.util.StockUtils.isLiveInMarket;

import com.interview.tradetracker.entity.Stock;
import com.interview.tradetracker.entity.StockExchange;
import com.interview.tradetracker.exception.StockAlreadyExistInStockExchangeException;
import com.interview.tradetracker.exception.StockExchangeAlreadyExistException;
import com.interview.tradetracker.exception.StockExchangeNotFoundException;
import com.interview.tradetracker.exception.StockNotFoundException;
import com.interview.tradetracker.repository.StockExchangeRepository;
import com.interview.tradetracker.request.CreateStockExchangeRequest;
import com.interview.tradetracker.service.StockExchangeService;
import com.interview.tradetracker.service.StockService;
import com.interview.tradetracker.util.DtoEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class StockExchangeServiceImpl implements StockExchangeService {

  private static final Logger logger = LoggerFactory.getLogger(StockExchangeServiceImpl.class);

  private final StockExchangeRepository stockExchangeRepository;

  private final StockService stockService;

  public StockExchangeServiceImpl(StockExchangeRepository stockExchangeRepository,
      StockService stockService) {
    this.stockExchangeRepository = stockExchangeRepository;
    this.stockService = stockService;
  }

  @Override
  public void create(CreateStockExchangeRequest request) throws StockExchangeAlreadyExistException {
    try {
      StockExchange stockExchange = DtoEntityMapper.getInstance().map(request, StockExchange.class);
      stockExchange.setLiveInMarket(false);
      stockExchangeRepository.save(stockExchange);
      logger.info("stock exchange {} created", stockExchange.getName());
    } catch (DataIntegrityViolationException e) {
      logger.error("stock exchange {} is already exist", request.getName());
      throw new StockExchangeAlreadyExistException(request.getName());
    }
  }

  @Override
  public StockExchange getStocks(String stockExchangeName) throws StockExchangeNotFoundException {
    return stockExchangeRepository.findByName(stockExchangeName)
        .orElseThrow(() -> new StockExchangeNotFoundException(stockExchangeName));
  }

  @Override
  public void addStock(String stockExchangeName, String stockName)
      throws StockExchangeNotFoundException, StockNotFoundException, StockAlreadyExistInStockExchangeException {
    StockExchange stockExchange = getStockExchange(stockExchangeName);
    Stock stock = stockService.getStockByName(stockName);
    if (stockExchange.getStocks().contains(stock)) {
      throw new StockAlreadyExistInStockExchangeException(stockName, stockExchangeName);
    }
    stockExchange.getStocks().add(stock);
    stockExchange.setLiveInMarket(isLiveInMarket(stockExchange.getStocks()));
    stockExchangeRepository.save(stockExchange);
    logger.info("Stock {} added to {}", stockName, stockExchange.getName());
  }

  @Override
  public void deleteStock(String stockExchangeName, String stockName)
      throws StockExchangeNotFoundException, StockNotFoundException {
    StockExchange stockExchange = getStockExchange(stockExchangeName);
    Stock stockToRemove = stockExchange.getStocks().stream()
        .filter(stock -> stock.getName().equals(stockName))
        .findFirst()
        .orElseThrow(() -> {
          logger.error("Stock not found: {}", stockName);
          return new StockNotFoundException(stockName);
        });

    stockExchange.getStocks().remove(stockToRemove);
    stockExchange.setLiveInMarket(isLiveInMarket(stockExchange.getStocks()));
    stockExchangeRepository.save(stockExchange);
    logger.info("Stock {} deleted from {}", stockName, stockExchange.getName());
  }

  private StockExchange getStockExchange(String stockExchangeName)
      throws StockExchangeNotFoundException {
    return stockExchangeRepository.findByName(
        stockExchangeName).orElseThrow(() -> new StockExchangeNotFoundException(stockExchangeName));
  }


}
