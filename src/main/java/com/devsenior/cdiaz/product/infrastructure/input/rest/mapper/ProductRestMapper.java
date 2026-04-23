package com.devsenior.cdiaz.product.infrastructure.input.rest.mapper;

import org.springframework.stereotype.Component;

import com.devsenior.cdiaz.product.domain.model.Product;
import com.devsenior.cdiaz.product.infrastructure.input.rest.dto.ProductRequest;
import com.devsenior.cdiaz.product.infrastructure.input.rest.dto.ProductResponse;

@Component
public class ProductRestMapper {
    // Transforma lo que entra desde la web al lenguaje del Dominio
    public Product toDomain(ProductRequest request) {
        return new Product(null, request.name(), request.price());
    }

    // Transforma el resultado del Dominio a un formato para la API web
    public ProductResponse toDto(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice());
    }

}
