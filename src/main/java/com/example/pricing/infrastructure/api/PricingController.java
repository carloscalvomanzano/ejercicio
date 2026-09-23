package com.example.pricing.infrastructure.api;

import com.example.pricing.application.PricingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/prices")
public class PricingController {

    private final PricingService service;

    public PricingController(PricingService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PriceResponse> getPrice(
            
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime applicationDate,
            Integer productId,
            Integer brandId) {

        return service.getApplicablePrice(brandId, productId, applicationDate)
                .map(price -> ResponseEntity.ok(PriceResponse.from(price)))
                .orElse(ResponseEntity.notFound().build());
    }
}
