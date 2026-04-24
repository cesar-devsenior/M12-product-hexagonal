package com.devsenior.cdiaz.product.infrastructure.output.jpa.mapper;

import com.devsenior.cdiaz.product.domain.model.Product;
import com.devsenior.cdiaz.product.infrastructure.output.jpa.ProductEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductJpaMapperTest {

    private final ProductJpaMapper mapper = new ProductJpaMapper();

    @Test
    void shouldMapProductToEntity() {
        // Given
        Product product = new Product(1L, "Test Product", BigDecimal.valueOf(10.99));

        // When
        ProductEntity entity = mapper.toEntity(product);

        // Then
        assertNull(entity.getId()); // ID not set in toEntity
        assertEquals("Test Product", entity.getName());
        assertEquals(BigDecimal.valueOf(10.99), entity.getPrice());
    }

    @Test
    void shouldMapEntityToDomain() {
        // Given
        ProductEntity entity = new ProductEntity(1L, "Test Product", BigDecimal.valueOf(10.99));

        // When
        Product product = mapper.toDomain(entity);

        // Then
        assertEquals(1L, product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals(BigDecimal.valueOf(10.99), product.getPrice());
    }

    @Test
    void shouldRoundTripMapping() {
        // Given
        Product original = new Product("Test Product", BigDecimal.valueOf(10.99));

        // When
        ProductEntity entity = mapper.toEntity(original);
        entity.setId(1L); // Simulate DB assigning ID
        Product roundTrip = mapper.toDomain(entity);

        // Then
        assertEquals(1L, roundTrip.getId());
        assertEquals(original.getName(), roundTrip.getName());
        assertEquals(original.getPrice(), roundTrip.getPrice());
    }
}