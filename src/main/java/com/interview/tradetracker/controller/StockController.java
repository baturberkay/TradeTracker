package com.interview.tradetracker.controller;

import com.interview.tradetracker.request.CreateStockRequest;
import com.interview.tradetracker.exception.StockAlreadyExistException;
import com.interview.tradetracker.exception.StockNotFoundException;
import com.interview.tradetracker.request.UpdatePriceRequest;
import com.interview.tradetracker.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Tag(name = "Stock", description = "The Stocks Api")
@RequestMapping("/api/v1/stock")
public class StockController {

  private final StockService stockService;


  public StockController(StockService stockService) {
    this.stockService = stockService;
  }

  @Operation(summary = "Create stock with required fields",
      description= "Create Stock through this endpoint")
  @PostMapping
  public ResponseEntity<HttpStatus> create(@RequestBody CreateStockRequest createStockRequest)
      throws StockAlreadyExistException {
    stockService.create(createStockRequest);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Operation(summary = "Update price of an exist stock",
      description= "Update stock price")
  @PutMapping
  public ResponseEntity<HttpStatus> updatePrice(@RequestBody UpdatePriceRequest request)
      throws StockNotFoundException {
    stockService.updatePrice(request.name(), request.newPrice());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Operation(summary = "Delete stock",
      description= "Deletes an exist stock")
  @DeleteMapping
  public ResponseEntity<HttpStatus> delete(@RequestBody String name) throws StockNotFoundException {
    stockService.delete(name);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
