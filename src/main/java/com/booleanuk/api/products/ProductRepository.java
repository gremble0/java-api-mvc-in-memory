package com.booleanuk.api.products;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepository {
  private int idCounter = 0;
  private List<Product> products = new ArrayList<>();

  public List<Product> getAll() {
    return this.products;
  }

  public Optional<List<Product>> getAllOfCategory(String category) {
    List<Product> ofCategory = this.products
        .stream()
        .filter(product -> product.category().equals(category))
        .toList();

    if (ofCategory.size() == 0)
      return Optional.empty();
    else
      return Optional.of(ofCategory);
  }

  public Optional<Product> create(String name, String category, int price) {
    if (this.products.stream()
        .filter(product -> product.name().equals(name))
        .findAny()
        .isPresent())
      return Optional.empty();

    Product product = new Product(this.idCounter++, name, category, price);
    this.products.add(product);

    return Optional.of(product);
  }

  public Optional<Product> getById(int id) {
    return this.products
        .stream()
        .filter(product -> product.id() == id)
        .findFirst();
  }

  public Optional<Product> removeById(int id) {
    return this.getById(id)
        .map(productToremove -> {
          this.products.remove(productToremove);
          return productToremove;
        });
  }
}
