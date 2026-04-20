package com.devsenior.cdiaz.product.infrastructure.input.rest.dto;

import java.math.BigDecimal;

public record ProductRequest(
        String name,
        BigDecimal price) {
}
