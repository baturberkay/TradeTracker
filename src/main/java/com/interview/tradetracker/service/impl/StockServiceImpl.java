package com.interview.tradetracker.service.impl;

import com.interview.tradetracker.entity.Stock;
import com.interview.tradetracker.exception.StockAlreadyExistException;
import com.interview.tradetracker.exception.StockNotFoundException;
import com.interview.tradetracker.repository.StockRepository;
import com.interview.tradetracker.request.CreateStockRequest;
import com.interview.tradetracker.service.StockLiveInMarketUpdateService;
import com.interview.tradetracker.service.StockService;
import com.interview.tradetracker.util.DtoEntityMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockServiceImpl implements StockService {

  protected static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

  private final StockRepository stockRepository;

  private final StockLiveInMarketUpdateService stockLiveInMarketUpdateService;

  public StockServiceImpl(StockRepository stockRepository,
      StockLiveInMarketUpdateService stockLiveInMarketUpdateService) {
    this.stockRepository = stockRepository;
    this.stockLiveInMarketUpdateService = stockLiveInMarketUpdateService;
  }

  @Override
  public Stock getStockByName(String name) throws StockNotFoundException {
    return stockRepository.findByName(name).orElseThrow(() -> {
      logger.error("Stock {} is not found", name);
      return new StockNotFoundException(name);
    });
  }

  @Override
  public void create(CreateStockRequest request) throws StockAlreadyExistException {
    try {
      Stock stock = DtoEntityMapper.getInstance().map(request, Stock.class);
      stockRepository.save(stock);
      logger.info("Stock {} created", stock.getName());
    } catch (DataIntegrityViolationException e) {
      logger.error("Stock {} is already exist", request.getName());
      throw new StockAlreadyExistException(request.getName());
    }
  }

  @Override
  public void updatePrice(String name, Double newPrice) throws StockNotFoundException {
    Stock stock = getStockByName(name);
    stock.setCurrentPrice(newPrice);
    stock.setLastUpdatedAt(LocalDateTime.now());
    stockRepository.save(stock);
    logger.info("The current price of the stock {} is updated with {}", stock.getName(), newPrice);
  }

  @Override
  @Transactional
  public void delete(String name) throws StockNotFoundException {
    Stock stock = stockRepository.findByName(name)
        .orElseThrow(() -> new StockNotFoundException(name));

    stockLiveInMarketUpdateService.updateLiveInMarketByStocksOnDelete(List.of(stock));
    stockRepository.delete(stock);
    logger.info("Stock {} is deleted", name);
  }
}
