package com.devsenior.cdiaz.product.infrastructure.input.rest.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        BigDecimal price) {

}
