package com.interview.tradetracker.serviceImpl;

import com.interview.tradetracker.entity.Stock;
import com.interview.tradetracker.exception.StockAlreadyExistException;
import com.interview.tradetracker.exception.StockNotFoundException;
import com.interview.tradetracker.repository.StockRepository;
import com.interview.tradetracker.request.CreateStockRequest;
import com.interview.tradetracker.service.StockService;
import com.interview.tradetracker.util.DtoEntityMapper;
import java.time.LocalDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockServiceImpl implements StockService {

  protected static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);


  private final StockRepository stockRepository;

  public StockServiceImpl(StockRepository stockRepository) {
    this.stockRepository = stockRepository;
  }

  @Override
  public Stock getStockByName(String name) throws StockNotFoundException {
    Optional<Stock> stockOptional = stockRepository.findByName(name);
    if (stockOptional.isEmpty()) {
      throw new StockNotFoundException(name);
    }
    return stockOptional.get();
  }

  @Override
  public void create(CreateStockRequest request) throws StockAlreadyExistException {
    try {
      Stock stock = DtoEntityMapper.getInstance().map(request, Stock.class);
      stockRepository.save(stock);
      logger.info("stock {} created", stock.getName());
    } catch (DataIntegrityViolationException e) {
      logger.error("stock {} is already exist", request.getName());
      throw new StockAlreadyExistException(request.getName());
    }
  }

  @Override
  public void updatePrice(String name, Double newPrice) throws StockNotFoundException {
    Optional<Stock> stockOptional = stockRepository.findByName(name);
    if (stockOptional.isEmpty()) {
      logger.error("stock {} is not found", name);
      throw new StockNotFoundException(name);
    }
    Stock stock = stockOptional.get();
    stock.setCurrentPrice(newPrice);
    stock.setLastUpdatedAt(LocalDateTime.now());
    stockRepository.save(stock);
    logger.info("The current price of the stock {} is updated with {}", stock.getName(), newPrice);
  }

  @Override
  @Transactional
  public void delete(String name) throws StockNotFoundException {
    int result = stockRepository.deleteByName(name);
    if (result < 1) {
      logger.error("stock {} is not found", name);
      throw new StockNotFoundException(name);
    }
    logger.info("stock {} is deleted", name);
  }
}
