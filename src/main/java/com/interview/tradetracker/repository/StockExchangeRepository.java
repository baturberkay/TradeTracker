package com.interview.tradetracker.repository;

import com.interview.tradetracker.entity.Stock;
import com.interview.tradetracker.entity.StockExchange;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockExchangeRepository extends JpaRepository<StockExchange, Long> {

  Optional<StockExchange> findByName(String name);

  Optional<List<StockExchange>> findStockExchangeByStocks(Collection<Stock> stocks);

}
