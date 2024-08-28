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

  public Product create(String name, String category, int price) {
    Product product = new Product(this.idCounter++, name, category, price);
    this.products.add(product);

    return product;
  }

  public Optional<Product> updateById(int id, UnidentifiedProduct newProduct) {
    return this.getById(id)
        .map(oldProduct -> {
          this.products.remove(oldProduct);
          return this.create(newProduct.name(), newProduct.category(), newProduct.price());
        });
  }
}
