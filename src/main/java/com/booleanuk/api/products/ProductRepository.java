package com.booleanuk.api.products;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductRepository {
  private int idCounter = 0;
  private List<Product> products = new ArrayList<>();

  public List<Product> getAll() {
    return this.products;
  }

  public List<Product> getAllOfCategory(String category) throws ResponseStatusException {
    List<Product> ofCategory = this.products
        .stream()
        .filter(product -> product.category().equals(category))
        .toList();

    if (ofCategory.size() == 0 && this.products.size() > 0)
      throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
    else
      return ofCategory;
  }

  public Product create(String name, String category, int price) throws ResponseStatusException {
    if (this.products.stream()
        .filter(product -> product.name().equals(name))
        .findAny()
        .isPresent())
      throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);

    Product product = new Product(this.idCounter++, name, category, price);
    this.products.add(product);

    return product;
  }

  public Product getById(int id) throws ResponseStatusException {
    return this.products
        .stream()
        .filter(product -> product.id() == id)
        .findFirst().orElseThrow(() -> {
          throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
        });
  }

  public Product removeById(int id) throws ResponseStatusException {
    Product toRemove = this.getById(id);
    this.products.remove(toRemove);
    return toRemove;
  }
}
