package com.oracle.service;

import com.oracle.beans.Product;
import java.util.List;

public interface ProductService {
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Long productId);
    List<Product> getAllProducts();
    Product getProductById(Long productId);
}
