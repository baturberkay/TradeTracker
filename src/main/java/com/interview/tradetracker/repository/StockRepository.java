package com.interview.tradetracker.repository;

import com.interview.tradetracker.entity.Stock;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

  Optional<Stock> findByName(String name);

  int deleteByName(String name);
}
