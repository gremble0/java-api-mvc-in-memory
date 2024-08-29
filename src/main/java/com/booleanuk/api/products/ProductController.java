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
  public ResponseEntity<List<Product>> getCategoryOrAll(@RequestParam(required = false) Optional<String> category)
      throws ResponseStatusException {
    if (category.isPresent())
      return new ResponseEntity<>(this.repository.getCategory(category.get().toLowerCase()), HttpStatus.OK);
    else
      return new ResponseEntity<>(this.repository.getAll(), HttpStatus.OK);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Product> getById(@PathVariable int id) throws ResponseStatusException {
    return new ResponseEntity<>(this.repository.getById(id), HttpStatus.OK);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Product> updateById(@PathVariable int id, @RequestBody ProductDTO newProductDTO)
      throws ResponseStatusException {
    return new ResponseEntity<>(this.repository.updateById(id, newProductDTO), HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Product> removeById(@PathVariable int id) throws ResponseStatusException {
    return new ResponseEntity<>(this.repository.removeById(id), HttpStatus.OK);
  }

  @GetMapping(value = "/418")
  public ResponseEntity<String> getTeapot() {
    return new ResponseEntity<>(this.repository.getTeapotTag(), HttpStatus.I_AM_A_TEAPOT);
  }
}
