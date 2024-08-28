package com.booleanuk.api.products;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
  private ProductRepository repository;

  public ProductController(ProductRepository repository) {
    this.repository = repository;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Product create(@RequestBody UnidentifiedProduct product) {
    return this.repository.create(product.name(), product.category(), product.price());
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Product> getAll() {
    return this.repository.getAll();
  }

  @GetMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Optional<Product> getById(@PathVariable int id) {
    return this.repository.getById(id);
  }
}
