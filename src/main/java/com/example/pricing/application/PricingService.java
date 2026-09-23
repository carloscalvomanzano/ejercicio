package com.example.pricing.application;

import com.example.pricing.domain.model.Price;
import com.example.pricing.domain.port.PriceRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PricingService {

    private final PriceRepositoryPort repository;

    public PricingService(PriceRepositoryPort repository) {
        this.repository = repository;
    }

    public Optional<Price> getApplicablePrice(
            Integer brandId,
            Integer productId,
            LocalDateTime applicationDate) {

        return repository.findApplicablePrice(brandId, productId, applicationDate);
    }
}
