package com.example.pricing.infrastructure.persistence;

import com.example.pricing.domain.model.Price;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "prices")
public class PriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer brandId;
    private Integer productId;
    private Integer priceList;
    private Integer priority;
    private BigDecimal price;
    private String curr;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Price toDomain() {
        return new Price(
                brandId, productId, priceList, priority,
                price, curr, startDate, endDate
        );
    }
}
