package com.devsenior.cdiaz.product.infrastructure.output.adapter;

import com.devsenior.cdiaz.product.domain.model.Product;
import com.devsenior.cdiaz.product.infrastructure.output.jpa.ProductEntity;
import com.devsenior.cdiaz.product.infrastructure.output.jpa.SpringDataProductRepository;
import com.devsenior.cdiaz.product.infrastructure.output.jpa.mapper.ProductJpaMapper;
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
class ProductPersistenceAdapterTest {

    @Mock
    private SpringDataProductRepository repository;

    @Mock
    private ProductJpaMapper mapper;

    @InjectMocks
    private ProductPersistenceAdapter adapter;

    @Test
    void shouldSaveProduct() {
        // Given
        Product product = new Product("Test Product", BigDecimal.valueOf(10.99));
        ProductEntity entity = new ProductEntity("Test Product", BigDecimal.valueOf(10.99));
        ProductEntity savedEntity = new ProductEntity(1L, "Test Product", BigDecimal.valueOf(10.99));
        Product savedProduct = new Product(1L, "Test Product", BigDecimal.valueOf(10.99));

        when(mapper.toEntity(product)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(savedEntity);
        when(mapper.toDomain(savedEntity)).thenReturn(savedProduct);

        // When
        Product result = adapter.save(product);

        // Then
        assertEquals(savedProduct, result);
        verify(mapper).toEntity(product);
        verify(repository).save(entity);
        verify(mapper).toDomain(savedEntity);
    }

    @Test
    void shouldFindProductByIdWhenExists() {
        // Given
        Long id = 1L;
        ProductEntity entity = new ProductEntity(id, "Test Product", BigDecimal.valueOf(10.99));
        Product product = new Product(id, "Test Product", BigDecimal.valueOf(10.99));

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(product);

        // When
        Optional<Product> result = adapter.findById(id);

        // Then
        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        verify(repository).findById(id);
        verify(mapper).toDomain(entity);
    }

    @Test
    void shouldReturnEmptyWhenProductNotFound() {
        // Given
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<Product> result = adapter.findById(id);

        // Then
        assertFalse(result.isPresent());
        verify(repository).findById(id);
        verify(mapper, never()).toDomain(any());
    }
}