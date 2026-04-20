package com.devsenior.cdiaz.product.infrastructure.output.jpa;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.devsenior.cdiaz.product.domain.model.Product;
import com.devsenior.cdiaz.product.domain.ports.out.ProductRepositoryPort;

@Component
public class ProductPersistenceAdapter implements ProductRepositoryPort {

    private final SpringDataProductRepository repository;

    public ProductPersistenceAdapter(SpringDataProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(Product product) {
        // 1. Mapear Dominio -> Entidad JPA
        ProductEntity entity = new ProductEntity();
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());

        // 2. Guardar en BD
        ProductEntity savedEntity = repository.save(entity);

        // 3. Mapear Entidad JPA -> Dominio
        return new Product(savedEntity.getId(), savedEntity.getName(), savedEntity.getPrice());
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id)
                .map(entity -> new Product(entity.getId(), entity.getName(), entity.getPrice()));
    }

}
