package com.booleanuk.api.products;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductRepository {
  private int idCounter = 0;
  private List<Product> products = new ArrayList<>();
  private static final String teapotTag = "<img src='https://media2.giphy.com/media/ARmZmMqobLtZKrJRrU/200w.gif?cid=6c09b95287okszcbz59ao584wd835y08z53ju0u1pwjpok20&ep=v1_gifs_search&rid=200w.gif&ct=g'>";

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
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    else
      return ofCategory;
  }

  public Product create(String name, String category, int price) throws ResponseStatusException {
    if (this.productWithNameExists(name))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

    Product product = new Product(this.idCounter++, name, category, price);
    this.products.add(product);

    return product;
  }

  public Product getById(int id) throws ResponseStatusException {
    return this.products
        .stream()
        .filter(product -> product.id() == id)
        .findFirst().orElseThrow(() -> {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
  }

  public Product removeById(int id) throws ResponseStatusException {
    Product toRemove = this.getById(id);
    this.products.remove(toRemove);
    return toRemove;
  }

  public Product updateById(int id, ProductDTO newProductDTO) throws ResponseStatusException {
    if (this.productWithNameExists(newProductDTO.name()))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

    // Can throw 404
    Product oldProduct = this.getById(id);
    this.products.remove(oldProduct);

    Product newProduct = new Product(id, newProductDTO.name(), newProductDTO.category(), newProductDTO.price());
    this.products.add(newProduct);

    return newProduct;
  }

  public String getTeapotTag() {
    return ProductRepository.teapotTag;
  }
}
