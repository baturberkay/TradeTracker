package com.interview.tradetracker.serviceImpl;

import com.interview.tradetracker.entity.Stock;
import com.interview.tradetracker.entity.StockExchange;
import com.interview.tradetracker.exception.StockExchangeAlreadyExistException;
import com.interview.tradetracker.exception.StockExchangeNotFoundException;
import com.interview.tradetracker.exception.StockNotFoundException;
import com.interview.tradetracker.repository.StockExchangeRepository;
import com.interview.tradetracker.request.CreateStockExchangeRequest;
import com.interview.tradetracker.service.StockExchangeService;
import com.interview.tradetracker.service.StockService;
import com.interview.tradetracker.util.DtoEntityMapper;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StockExchangeServiceImpl implements StockExchangeService {

  protected static final Logger logger = LoggerFactory.getLogger(StockExchangeServiceImpl.class);

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
  @Transactional
  public StockExchange getStocks(String stockExchangeName) {
    return stockExchangeRepository.findByName(stockExchangeName).get();
  }

  @Override
  @Transactional
  public void addStock(String stockExchangeName, String stockName)
      throws StockExchangeNotFoundException, StockNotFoundException {
    StockExchange stockExchange = getStockExchange(stockExchangeName);
    Stock stock = stockService.getStockByName(stockName);
    stockExchange.getStocks().add(stock);
    if (isLiveInMarket(stockExchange.getStocks())) {
      stockExchange.setLiveInMarket(true);
    }
    stockExchangeRepository.save(stockExchange);
    logger.info("Stock {} added to {}", stockName, stockExchange.getName());
  }

  @Override
  @Transactional
  public void deleteStock(String stockExchangeName, String stockName)
      throws StockExchangeNotFoundException, StockNotFoundException {
    StockExchange stockExchange = getStockExchange(stockExchangeName);

    Stock stockToRemove = stockExchange.getStocks().stream()
        .filter(stock -> stock.getName().equals(stockName))
        .findFirst()
        .orElseThrow(() -> {
          // Log the exception details with the stock name
          logger.error("Stock not found: {}", stockName);
          return new StockNotFoundException(stockName);
        });

    stockExchange.getStocks().remove(stockToRemove);
    if (!isLiveInMarket(stockExchange.getStocks())) {
      stockExchange.setLiveInMarket(false);
    }
    stockExchangeRepository.save(stockExchange);
    logger.info("Stock {} deleted from {}", stockName, stockExchange.getName());
  }

  private StockExchange getStockExchange(String stockExchangeName)
      throws StockExchangeNotFoundException {
    Optional<StockExchange> stockExchangeOptional = stockExchangeRepository.findByName(
        stockExchangeName);
    if (stockExchangeOptional.isEmpty()) {
      throw new StockExchangeNotFoundException(stockExchangeName);
    }
    return stockExchangeOptional.get();
  }

  private static boolean isLiveInMarket(Set<Stock> stocks) {
    return stocks.size() >= 5;
  }
}
