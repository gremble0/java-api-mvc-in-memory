package com.booleanuk.api.products;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductRepository {
  private int idCounter = 0;
  private List<Product> products = new ArrayList<>();
  private static final String teapotTag = "<img src='https://media2.giphy.com/media/ARmZmMqobLtZKrJRrU/200w.gif?cid=6c09b95287okszcbz59ao584wd835y08z53ju0u1pwjpok20&ep=v1_gifs_search&rid=200w.gif&ct=g'>";

  private boolean productWithNameExists(String name) {
    return this.products
        .stream()
        .filter(product -> product.name().equals(name))
        .findAny()
        .isPresent();
  }

  private Product productFromDTO(ProductDTO productDTO) {
    return new Product(this.idCounter++, productDTO.name(), productDTO.category(), productDTO.price());
  }

  public List<Product> getAll() {
    return this.products;
  }

  public List<Product> getCategory(String category) throws ResponseStatusException {
    List<Product> ofCategory = this.products
        .stream()
        .filter(product -> product.category().equalsIgnoreCase(category))
        .toList();

    if (ofCategory.size() == 0 && this.products.size() > 0)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "No products of the provided category '" + category + "' were found");
    else
      return ofCategory;
  }

  public Product create(ProductDTO productDTO) throws ResponseStatusException {
    if (this.productWithNameExists(productDTO.name()))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Product with name '" + productDTO.name() + "' already exists");

    Product product = this.productFromDTO(productDTO);
    this.products.add(product);

    return product;
  }

  public Product getById(int id) throws ResponseStatusException {
    return this.products
        .stream()
        .filter(product -> product.id() == id)
        .findFirst()
        .orElseThrow(() -> {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id '" + id + "' not found");
        });
  }

  public Product removeById(int id) throws ResponseStatusException {
    // Can throw 404
    Product toRemove = this.getById(id);
    this.products.remove(toRemove);
    return toRemove;
  }

  public Product updateById(int id, ProductDTO productDTO) throws ResponseStatusException {
    if (this.productWithNameExists(productDTO.name()))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Product with name '" + productDTO.name() + "' already exists");

    // Can throw 404
    Product oldProduct = this.getById(id);
    this.products.remove(oldProduct);

    Product newProduct = this.productFromDTO(productDTO);
    this.products.add(newProduct);

    return newProduct;
  }

  // Not part of the assignment
  public String getTeapotTag() {
    return ProductRepository.teapotTag;
  }
}
