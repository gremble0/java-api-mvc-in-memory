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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
  private ProductRepository repository = new ProductRepository();

  // TODO: ResponseEntity<Product> instead of '?'
  @PostMapping
  public ResponseEntity<?> create(@RequestBody UnidentifiedProduct product) {
    Optional<Product> productWithId = this.repository.create(product.name(), product.category(), product.price());

    if (productWithId.isEmpty())
      return new ResponseEntity<>("Product with provided name already exists", HttpStatus.BAD_REQUEST);
    else
      return new ResponseEntity<>(productWithId.get(), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<?> getCategoryOrAll(@RequestParam(required = false) String category) {
    Optional<List<Product>> ofCategory = this.repository.getAllOfCategory(category);

    if (ofCategory.isEmpty())
      return new ResponseEntity<>(this.repository.getAll(), HttpStatus.NOT_FOUND);
    else
      return new ResponseEntity<>(ofCategory, HttpStatus.OK);
  }

  @GetMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> getById(@PathVariable int id) {
    Optional<Product> product = this.repository.getById(id);

    if (product.isEmpty())
      return new ResponseEntity<>("No product with id '" + id + "' found", HttpStatus.NOT_FOUND);
    else
      return new ResponseEntity<>(product.get(), HttpStatus.OK);
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
