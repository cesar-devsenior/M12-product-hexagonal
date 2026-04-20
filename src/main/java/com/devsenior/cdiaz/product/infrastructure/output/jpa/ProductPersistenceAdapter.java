package com.devsenior.cdiaz.product.infrastructure.output.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.devsenior.cdiaz.product.domain.model.Product;
import com.devsenior.cdiaz.product.domain.ports.out.ProductRepositoryPort;
import com.devsenior.cdiaz.product.infrastructure.input.rest.mapper.ProductRestMapper;

@Component
public class ProductPersistenceAdapter implements ProductRepositoryPort {

    private final SpringDataProductRepository repository;
    private final ProductRestMapper mapper;

    public ProductPersistenceAdapter(SpringDataProductRepository repository, ProductRestMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Product save(Product product) {
        // 1. Mapear Dominio -> Entidad JPA
        var entity = mapper.toEntity(product);

        // 2. Guardar en BD
        var savedEntity = repository.save(entity);

        // 3. Mapear Entidad JPA -> Dominio
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

}
