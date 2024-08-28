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

  public Optional<Product> getById(int id) {
    return this.products
        .stream()
        .filter(product -> product.id() == id)
        .findFirst();
  }

  public void create(String name, String category, int price) {
    this.products.add(new Product(this.idCounter++, name, category, price));
  }
}
