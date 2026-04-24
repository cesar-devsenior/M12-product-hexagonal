package com.devsenior.cdiaz.product.application.service;

import com.devsenior.cdiaz.product.domain.exception.ProductNotFoundException;
import com.devsenior.cdiaz.product.domain.model.Product;
import com.devsenior.cdiaz.product.domain.ports.out.ProductRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepositoryPort productRepositoryPort;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldCreateProductSuccessfully() {
        // Given
        Product product = new Product("Test Product", BigDecimal.valueOf(10.99));
        Product savedProduct = new Product(1L, "Test Product", BigDecimal.valueOf(10.99));
        when(productRepositoryPort.save(any(Product.class))).thenReturn(savedProduct);

        // When
        Product result = productService.createProduct(product);

        // Then
        assertEquals(savedProduct, result);
        verify(productRepositoryPort).save(product);
    }

    @Test
    void shouldGetProductByIdWhenFound() {
        // Given
        Long id = 1L;
        Product product = new Product(id, "Test Product", BigDecimal.valueOf(10.99));
        when(productRepositoryPort.findById(id)).thenReturn(Optional.of(product));

        // When
        Product result = productService.getProductById(id);

        // Then
        assertEquals(product, result);
        verify(productRepositoryPort).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        // Given
        Long id = 1L;
        when(productRepositoryPort.findById(id)).thenReturn(Optional.empty());

        // When & Then
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
            () -> productService.getProductById(id));
        assertEquals("Producto no encontrado", exception.getMessage());
        verify(productRepositoryPort).findById(id);
    }
}