package com.devsenior.cdiaz.product.domain.model;

import java.math.BigDecimal;

public class Product {
    private Long id;
    private String name;
    private BigDecimal price;

    public Product(String name, BigDecimal price) {
        this(null, name, price);
    }

    public Product(Long id, String name, BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero");
        }
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
