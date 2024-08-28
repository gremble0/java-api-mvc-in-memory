package com.booleanuk.api.products;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
  private ProductRepository repository = new ProductRepository();

  @PostMapping
  public ResponseEntity<?> create(@RequestBody UnidentifiedProduct product) {
    Optional<Product> productWithId = this.repository.create(product.name(), product.category(), product.price());

    if (productWithId.isEmpty())
      return new ResponseEntity<>("Product with provided name already exists", HttpStatus.BAD_REQUEST);
    else
      return new ResponseEntity<>(productWithId, HttpStatus.CREATED);
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

  @PutMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  public Optional<Product> updateById(@PathVariable int id, @RequestBody UnidentifiedProduct newProduct) {
    return this.repository.updateById(id, newProduct);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Optional<Product> removeById(@PathVariable int id) {
    return this.repository.removeById(id);
  }
}
