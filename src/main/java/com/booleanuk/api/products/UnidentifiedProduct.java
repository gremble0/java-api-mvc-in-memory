package com.booleanuk.api.products;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UnidentifiedProduct(
    @JsonProperty(required = true) String name,
    @JsonProperty(required = true) String category,
    @JsonProperty(required = true) int price) {
}
