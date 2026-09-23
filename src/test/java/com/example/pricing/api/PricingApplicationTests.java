package com.example.pricing.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class PricingApplicationTests {

    @Autowired
    private RestTestClient restClient;

    @Test
    void test1() {
        restClient.get()
            .uri("/api/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.priceList").isEqualTo(1)
            .jsonPath("$.price").isEqualTo(35.50);
    }

    @Test
    void test2() {
        restClient.get()
            .uri("/api/prices?applicationDate=2020-06-14T16:00:00&productId=35455&brandId=1")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.priceList").isEqualTo(2)
            .jsonPath("$.price").isEqualTo(25.45);
    }

    @Test
    void test3() {
        restClient.get()
            .uri("/api/prices?applicationDate=2020-06-14T21:00:00&productId=35455&brandId=1")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.priceList").isEqualTo(1)
            .jsonPath("$.price").isEqualTo(35.50);
    }

    @Test
    void test4() {
        restClient.get()
            .uri("/api/prices?applicationDate=2020-06-15T10:00:00&productId=35455&brandId=1")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.priceList").isEqualTo(3)
            .jsonPath("$.price").isEqualTo(30.50);
    }

    @Test
    void test5() {
        restClient.get()
            .uri("/api/prices?applicationDate=2020-06-16T21:00:00&productId=35455&brandId=1")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.priceList").isEqualTo(4)
            .jsonPath("$.price").isEqualTo(38.95);
    }
}
