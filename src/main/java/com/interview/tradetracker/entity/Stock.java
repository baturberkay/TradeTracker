package com.interview.tradetracker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Stock implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STOCK_SEQ_GEN")
  @SequenceGenerator(name = "STOCK_SEQ_GEN", sequenceName = "STOCK_SEQ_GEN")
  Long id;
  @Column(unique = true, nullable = false)
  String name;
  String description;

  @Column(nullable = false)
  Double currentPrice;
  LocalDateTime lastUpdatedAt;

  @OnDelete(action = OnDeleteAction.CASCADE)
  @ManyToMany(mappedBy = "stocks")
  @JsonBackReference
  private Set<StockExchange> stockExchanges = new HashSet<>();


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getCurrentPrice() {
    return currentPrice;
  }

  public void setCurrentPrice(Double currentPrice) {
    this.currentPrice = currentPrice;
  }

  public LocalDateTime getLastUpdatedAt() {
    return lastUpdatedAt;
  }

  public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
    this.lastUpdatedAt = lastUpdatedAt;
  }

  public Set<StockExchange> getStockExchanges() {
    return stockExchanges;
  }

  public void setStockExchanges(
      Set<StockExchange> stockExchanges) {
    this.stockExchanges = stockExchanges;
  }
}
