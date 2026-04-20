package com.devsenior.cdiaz.product.application.usecase;

import java.util.List;

import com.devsenior.cdiaz.product.domain.model.Product;

public interface GetProductUseCase {
    Product getProductById(Long id);

    List<Product> getAllProducts();
}
