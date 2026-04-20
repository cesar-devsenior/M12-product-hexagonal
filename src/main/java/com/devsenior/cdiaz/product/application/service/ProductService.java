package com.devsenior.cdiaz.product.application.service;

import java.util.List;

import com.devsenior.cdiaz.product.application.usecase.CreateProductUseCase;
import com.devsenior.cdiaz.product.application.usecase.GetProductUseCase;
import com.devsenior.cdiaz.product.domain.exception.ProductNotFoundException;
import com.devsenior.cdiaz.product.domain.model.Product;
import com.devsenior.cdiaz.product.domain.ports.out.ProductRepositoryPort;

public class ProductService implements CreateProductUseCase, GetProductUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    public ProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    public Product getProductById(Long id) {
        return productRepositoryPort.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));
    }

    @Override
    public Product createProduct(Product product) {
        return productRepositoryPort.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepositoryPort.findAll();
    }

}
