package com.devsenior.cdiaz.product.domain.ports.out;

import java.util.List;
import java.util.Optional;

import com.devsenior.cdiaz.product.domain.model.Product;

public interface ProductRepositoryPort {
    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

}
