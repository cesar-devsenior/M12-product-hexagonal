package com.devsenior.cdiaz.product.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

class ProductTest {

    @Test
    void shouldCreateValidProductWithAllFields() {
        // Given
        Long id = 1L;
        String name = "Test Product";
        BigDecimal price = BigDecimal.valueOf(10.99);

        // When
        Product product = new Product(id, name, price);

        // Then
        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(price, product.getPrice());
    }

    @Test
    void shouldCreateValidProductWithoutId() {
        // Given
        String name = "Test Product";
        BigDecimal price = BigDecimal.valueOf(10.99);

        // When
        Product product = new Product(name, price);

        // Then
        assertNull(product.getId());
        assertEquals(name, product.getName());
        assertEquals(price, product.getPrice());
    }

    @Test
    void shouldThrowExceptionForNullPrice() {
        // Given
        String name = "Test Product";
        BigDecimal price = null;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new Product(name, price));
        assertEquals("El precio debe ser mayor a cero", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForZeroPrice() {
        // Given
        String name = "Test Product";
        BigDecimal price = BigDecimal.ZERO;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new Product(name, price));
        assertEquals("El precio debe ser mayor a cero", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNegativePrice() {
        // Given
        String name = "Test Product";
        BigDecimal price = BigDecimal.valueOf(-1.00);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new Product(name, price));
        assertEquals("El precio debe ser mayor a cero", exception.getMessage());
    }

    @Test
    void shouldSetAndGetId() {
        // Given
        Product product = new Product("Test", BigDecimal.ONE);
        Long newId = 2L;

        // When
        product.setId(newId);

        // Then
        assertEquals(newId, product.getId());
    }

    @Test
    void shouldSetAndGetName() {
        // Given
        Product product = new Product("Test", BigDecimal.ONE);
        String newName = "New Name";

        // When
        product.setName(newName);

        // Then
        assertEquals(newName, product.getName());
    }

    @Test
    void shouldSetAndGetPrice() {
        // Given
        Product product = new Product("Test", BigDecimal.ONE);
        BigDecimal newPrice = BigDecimal.valueOf(20.00);

        // When
        product.setPrice(newPrice);

        // Then
        assertEquals(newPrice, product.getPrice());
    }
}