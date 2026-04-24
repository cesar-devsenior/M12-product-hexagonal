package com.devsenior.cdiaz.product.infrastructure.input.rest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.devsenior.cdiaz.product.infrastructure.input.rest.dto.ProductRequest;
import com.devsenior.cdiaz.product.infrastructure.input.rest.dto.ProductResponse;
import com.devsenior.cdiaz.product.infrastructure.output.jpa.ProductEntity;
import com.devsenior.cdiaz.product.infrastructure.output.jpa.SpringDataProductRepository;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SpringDataProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void shouldCreateProductSuccessfully() throws Exception {
        // Given
        var request = new ProductRequest("Laptop", BigDecimal.valueOf(999.99));

        // When & Then
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.price").value(999.99))
                .andReturn();
    }

    @Test
    void shouldGetProductByIdSuccessfully() throws Exception {
        // Given
        var entity = new ProductEntity("Mouse", BigDecimal.valueOf(20.50));
        var savedEntity = productRepository.save(entity);

        // When & Then
        mockMvc.perform(get("/api/products/{id}", savedEntity.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedEntity.getId()))
                .andExpect(jsonPath("$.name").value("Mouse"))
                .andExpect(jsonPath("$.price").value(20.50))
                .andReturn();
    }

    @Test
    void shouldReturnNotFoundWhenProductDoesNotExist() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/products/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Producto no encontrado"))
                .andReturn();
    }

    @Test
    void shouldReturnBadRequestForInvalidPrice() throws Exception {
        // Given - invalid price (zero)
        var invalidRequest = new ProductRequest("Invalid Product", BigDecimal.ZERO);

        // When & Then
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").exists())
                .andReturn();
    }

    @Test
    void shouldReturnBadRequestForNegativePrice() throws Exception {
        // Given - negative price
        var invalidRequest = new ProductRequest("Negative Price", BigDecimal.valueOf(-10.00));

        // When & Then
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andReturn();
    }

    @Test
    void shouldCreateAndRetrieveProduct() throws Exception {
        // Given
        var createRequest = new ProductRequest("Keyboard", BigDecimal.valueOf(75.00));

        // When - Create
        var createResult = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        // Extract and verify creation response
        var createResponse = createResult.getResponse().getContentAsString();
        assertNotNull(createResponse, "Creation response should not be null");

        var createdProduct = objectMapper.readValue(createResponse, ProductResponse.class);
        var productId = createdProduct.id();

        // Then - Retrieve
        mockMvc.perform(get("/api/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value("Keyboard"))
                .andExpect(jsonPath("$.price").value(75.00))
                .andReturn();
    }

    @Test
    void shouldCreateMultipleProductsIndependently() throws Exception {
        // Given
        var request1 = new ProductRequest("Product 1", BigDecimal.valueOf(10.00));
        var request2 = new ProductRequest("Product 2", BigDecimal.valueOf(20.00));

        // When - Create first product
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request1)))
                .andExpect(status().isCreated())
                .andReturn();

        // When - Create second product
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request2)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void shouldHandleInvalidJsonRequest() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("invalid json"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void shouldResponseIncludeTimestampOnError() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/products/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.message").exists())
                .andReturn();
    }
}
