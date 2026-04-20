package com.devsenior.cdiaz.product.infrastructure.output.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Long> {

}
