package com.interview.tradetracker.exceptionController;


import com.interview.tradetracker.exception.StockAlreadyExistException;
import com.interview.tradetracker.exception.StockAlreadyExistInStockExchangeException;
import com.interview.tradetracker.exception.StockExchangeAlreadyExistException;
import com.interview.tradetracker.exception.StockExchangeNotFoundException;
import com.interview.tradetracker.exception.StockNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

  @ExceptionHandler(value = NullPointerException.class)
  public final ResponseEntity<HttpStatus> handleNullPointerException(NullPointerException e) {
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = StockAlreadyExistException.class)
  public final ResponseEntity<String> handleStockAlreadyExistException(
      StockAlreadyExistException e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }

  @ExceptionHandler(value = StockExchangeAlreadyExistException.class)
  public final ResponseEntity<String> handleStockExchangeAlreadyExistException(
      StockExchangeAlreadyExistException e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }


  @ExceptionHandler(value = StockAlreadyExistInStockExchangeException.class)
  public final ResponseEntity<String> handleStockAlreadyExistInStockExchangeException(
      StockAlreadyExistInStockExchangeException e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }

  @ExceptionHandler(value = StockNotFoundException.class)
  public final ResponseEntity<String> handleStockNotFoundException(StockNotFoundException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = StockExchangeNotFoundException.class)
  public final ResponseEntity<String> handleStockExchangeNotFoundException(
      StockExchangeNotFoundException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }
}
