package com.interview.tradetracker.request;

public record CreateStockRequest(Long id,
                                 String name,
                                 String description,
                                 Double currentPrice) {

}
