package com.devsenior.cdiaz.product.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.devsenior.cdiaz.product.application.service.ProductService;
import com.devsenior.cdiaz.product.domain.ports.out.ProductRepositoryPort;

@Configuration
public class DomainConfig {

    @Bean
    ProductService productService(ProductRepositoryPort productRepositoryPort){
        return new ProductService(productRepositoryPort);
    }
}
