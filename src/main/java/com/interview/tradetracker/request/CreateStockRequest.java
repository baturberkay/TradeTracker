package com.interview.tradetracker.dto;

public record CreateStockRequest(Long id,
                                 String name,
                                 String description,
                                 Double currentPrice) {

}
