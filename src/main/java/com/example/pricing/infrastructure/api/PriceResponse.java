package com.example.pricing.infrastructure.api;

import com.example.pricing.domain.model.Price;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceResponse {

    private Integer productId;
    private Integer brandId;
    private Integer priceList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal price;

    public static PriceResponse from(Price p) {
        PriceResponse r = new PriceResponse();
        r.productId = p.getProductId();
        r.brandId = p.getBrandId();
        r.priceList = p.getPriceList();
        r.startDate = p.getStartDate();
        r.endDate = p.getEndDate();
        r.price = p.getPrice();
        return r;
    }

    public Integer getProductId() { return productId; }
    public Integer getBrandId() { return brandId; }
    public Integer getPriceList() { return priceList; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public BigDecimal getPrice() { return price; }
}
