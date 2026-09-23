package com.example.pricing.infrastructure.persistence;

import com.example.pricing.domain.model.Price;
import com.example.pricing.domain.port.PriceRepositoryPort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class PriceRepositoryAdapter implements PriceRepositoryPort {

    private final SpringDataPriceRepository jpaRepository;

    public PriceRepositoryAdapter(SpringDataPriceRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Price> findApplicablePrice(
            Integer brandId,
            Integer productId,
            LocalDateTime applicationDate) {

        return jpaRepository.findApplicablePrices(brandId, productId, applicationDate)
                .stream()
                .findFirst()
                .map(PriceEntity::toDomain);
    }
}
