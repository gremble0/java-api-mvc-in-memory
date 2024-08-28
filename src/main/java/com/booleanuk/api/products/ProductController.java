package com.booleanuk.api.products;

public class ProductController {
  private ProductRepository repository;

  public ProductController(ProductRepository repository) {
    this.repository = repository;
  }
}
