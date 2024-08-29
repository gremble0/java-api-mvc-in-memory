package com.booleanuk.api.products;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductRepository {
  private int idCounter = 0;
  private List<Product> products = new ArrayList<>();

  public List<Product> getAll() {
    return this.products;
  }

  private boolean productWithNameExists(String name) {
    return this.products.stream()
        .filter(product -> product.name().equals(name))
        .findAny()
        .isPresent();
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
    if (this.productWithNameExists(name))
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

  public Product updateById(int id, ProductDTO newProductDTO) throws ResponseStatusException {
    if (this.productWithNameExists(newProductDTO.name()))
      throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);

    Product oldProduct = this.getById(id);
    this.products.remove(oldProduct);

    Product newProduct = new Product(id, newProductDTO.name(), newProductDTO.category(), newProductDTO.price());
    this.products.add(newProduct);

    return newProduct;
  }
}
