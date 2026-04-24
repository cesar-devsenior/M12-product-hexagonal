package com.devsenior.cdiaz.product.domain.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        // Given
        String message = "Product not found";

        // When
        ProductNotFoundException exception = new ProductNotFoundException(message);

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void shouldBeRuntimeException() {
        // Given
        ProductNotFoundException exception = new ProductNotFoundException("test");

        // Then
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void shouldHaveCorrectMessage() {
        // Given
        String expectedMessage = "Product with id 1 not found";

        // When
        ProductNotFoundException exception = new ProductNotFoundException(expectedMessage);

        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }
}