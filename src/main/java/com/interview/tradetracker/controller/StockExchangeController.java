package com.interview.tradetracker.controller;

import com.interview.tradetracker.entity.StockExchange;
import com.interview.tradetracker.exception.StockAlreadyExistInStockExchangeException;
import com.interview.tradetracker.exception.StockExchangeAlreadyExistException;
import com.interview.tradetracker.exception.StockExchangeNotFoundException;
import com.interview.tradetracker.exception.StockNotFoundException;
import com.interview.tradetracker.request.CreateStockExchangeRequest;
import com.interview.tradetracker.service.StockExchangeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Stock Exchange", description = "The Stock Exchange Api")
@RequestMapping("/api/v1/stock-exchange")
public class StockExchangeController {

  private final StockExchangeService stockExchangeService;

  public StockExchangeController(StockExchangeService stockExchangeService) {
    this.stockExchangeService = stockExchangeService;
  }

  @Operation(summary = "Create stock exchange with required fields",
      description = "Create Stock exchange through this endpoint")
  @PostMapping
  public ResponseEntity<HttpStatus> create(@RequestBody CreateStockExchangeRequest request)
      throws StockExchangeAlreadyExistException {
    stockExchangeService.create(request);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Operation(summary = "Get all stocks with stock exchange by stock exchange name",
      description = "Get all stock details with stock exchange by stock exchange name")
  @GetMapping("/{name}")
  public ResponseEntity<StockExchange> getAllStocks(@PathVariable String name)
      throws StockExchangeNotFoundException {
    return ResponseEntity.ok(stockExchangeService.getStocks(name));
  }

  @Operation(summary = "Add stock to stock exchange",
      description = "Add stock to stock exchange by stock name and stock exchange name")
  @PutMapping("/{name}/stock/{stockName}")
  public ResponseEntity<HttpStatus> addStock(@PathVariable String name,
      @PathVariable String stockName)
      throws StockExchangeNotFoundException, StockNotFoundException, StockAlreadyExistInStockExchangeException {
    stockExchangeService.addStock(name, stockName);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Operation(summary = "Remove stock from stock exchange",
      description = "Removes stock from stock exchange by stock name and stock exchange name")
  @DeleteMapping("/{name}/stock/{stockName}")
  public ResponseEntity<HttpStatus> deleteStock(@PathVariable String name,
      @PathVariable String stockName)
      throws StockExchangeNotFoundException, StockNotFoundException {
    stockExchangeService.deleteStock(name, stockName);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
