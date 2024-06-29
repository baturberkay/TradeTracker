package com.interview.tradetracker.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class StockExchange implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STOCK_EXC_SEQ_GEN")
  @SequenceGenerator(name = "STOCK_EXC_SEQ_GEN", sequenceName = "STOCK_EXC_SEQ_GEN")
  Long id;
  @Column(unique = true, nullable = false)
  String name;
  String description;
  Boolean liveInMarket;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonManagedReference
  @JoinTable(
      name = "StockExchange_Stock",
      joinColumns = {@JoinColumn(name = "stock_exchange_id")},
      inverseJoinColumns = {@JoinColumn(name = "stock_id")}
  )
  private Set<Stock> stocks = new HashSet<>();

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

  public Boolean getLiveInMarket() {
    return liveInMarket;
  }

  public void setLiveInMarket(Boolean liveInMarket) {
    this.liveInMarket = liveInMarket;
  }

  public Set<Stock> getStocks() {
    return stocks;
  }

  public void setStocks(Set<Stock> stocks) {
    this.stocks = stocks;
  }
}
