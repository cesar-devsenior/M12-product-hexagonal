package com.devsenior.cdiaz.product.infrastructure.input.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsenior.cdiaz.product.application.usecase.CreateProductUseCase;
import com.devsenior.cdiaz.product.application.usecase.GetProductUseCase;
import com.devsenior.cdiaz.product.domain.model.Product;
import com.devsenior.cdiaz.product.infrastructure.input.rest.dto.ProductRequest;
import com.devsenior.cdiaz.product.infrastructure.input.rest.dto.ProductResponse;
import com.devsenior.cdiaz.product.infrastructure.input.rest.mapper.ProductRestMapper;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final ProductRestMapper productRestMapper;

    public ProductController(CreateProductUseCase createProductUseCase, GetProductUseCase getProductUseCase,
            ProductRestMapper productRestMapper) {
        this.createProductUseCase = createProductUseCase;
        this.getProductUseCase = getProductUseCase;
        this.productRestMapper = productRestMapper;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest request) {
        // 1. Mapear DTO a Dominio usando el Mapper
        Product product = productRestMapper.toDomain(request);

        // 2. Ejecutar caso de uso (La capa de Aplicación/Dominio hace su magia)
        Product createdProduct = createProductUseCase.createProduct(product);

        // 3. Mapear objeto Dominio a Response DTO
        ProductResponse response = productRestMapper.toDto(createdProduct);

        // 4. Retornar DTO en lugar del Dominio directamente
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        var product = getProductUseCase.getProductById(id);

        var response = productRestMapper.toDto(product);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {
        var response = getProductUseCase.getAllProducts().stream()
            .map(productRestMapper::toDto)
            .toList();
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
