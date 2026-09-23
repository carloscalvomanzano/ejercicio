package com.example.pricing.domain.port;

import com.example.pricing.domain.model.Price;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepositoryPort {

    Optional<Price> findApplicablePrice(
            Integer brandId,
            Integer productId,
            LocalDateTime applicationDate);
}
