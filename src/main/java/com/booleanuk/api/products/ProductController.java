package com.booleanuk.api.products;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
  private ProductRepository repository = new ProductRepository();

  @PostMapping
  public ResponseEntity<Product> create(@RequestBody ProductDTO product) throws ResponseStatusException {
    return new ResponseEntity<>(this.repository.create(product.name(), product.category(), product.price()),
        HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<Product>> getCategoryOrAll(@RequestParam(required = false) String category)
      throws ResponseStatusException {
    return new ResponseEntity<>(this.repository.getAllOfCategory(category), HttpStatus.OK);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Product> getById(@PathVariable int id) throws ResponseStatusException {
    return new ResponseEntity<>(this.repository.getById(id), HttpStatus.OK);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Product> updateById(@PathVariable int id, @RequestBody ProductDTO newProduct)
      throws ResponseStatusException {
    // TODO: make update instead of remove + post
    // TODO: look into http status codes here - 400/404
    Product oldProduct = this.repository.removeById(id);

    return new ResponseEntity<>(this.repository.create(newProduct.name(), newProduct.category(),
        newProduct.price()), HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Product> removeById(@PathVariable int id) throws ResponseStatusException {
    return new ResponseEntity<>(this.repository.removeById(id), HttpStatus.OK);
  }
}
