package com.devsenior.cdiaz.product.infrastructure.output.jpa.mapper;

import org.springframework.stereotype.Component;

import com.devsenior.cdiaz.product.domain.model.Product;
import com.devsenior.cdiaz.product.infrastructure.output.jpa.ProductEntity;

@Component
public class ProductJpaMapper {
    
    // Transforma el resultado del Dominio a un formato para JPA
    public ProductEntity toEntity(Product product) {
        return new ProductEntity(product.getName(), product.getPrice());
    }

    // Transforma el resultado de JPA a un formato para el Dominio
    public Product toDomain(ProductEntity entity) {
        return new Product(entity.getId(), entity.getName(), entity.getPrice());
    }
}
