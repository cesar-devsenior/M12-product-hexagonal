package com.devsenior.cdiaz.product.infrastructure.input.rest.mapper;

import com.devsenior.cdiaz.product.domain.model.Product;
import com.devsenior.cdiaz.product.infrastructure.input.rest.dto.ProductRequest;
import com.devsenior.cdiaz.product.infrastructure.input.rest.dto.ProductResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductRestMapperTest {

    private final ProductRestMapper mapper = new ProductRestMapper();

    @Test
    void shouldMapProductRequestToDomain() {
        // Given
        ProductRequest request = new ProductRequest("Test Product", BigDecimal.valueOf(10.99));

        // When
        Product product = mapper.toDomain(request);

        // Then
        assertNull(product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals(BigDecimal.valueOf(10.99), product.getPrice());
    }

    @Test
    void shouldMapProductToDto() {
        // Given
        Product product = new Product(1L, "Test Product", BigDecimal.valueOf(10.99));

        // When
        ProductResponse response = mapper.toDto(product);

        // Then
        assertEquals(1L, response.id());
        assertEquals("Test Product", response.name());
        assertEquals(BigDecimal.valueOf(10.99), response.price());
    }

    @Test
    void shouldMapProductWithoutIdToDto() {
        // Given
        Product product = new Product("Test Product", BigDecimal.valueOf(10.99));

        // When
        ProductResponse response = mapper.toDto(product);

        // Then
        assertNull(response.id());
        assertEquals("Test Product", response.name());
        assertEquals(BigDecimal.valueOf(10.99), response.price());
    }
}