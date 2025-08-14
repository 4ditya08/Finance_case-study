package com.oracle.service.impl;

import com.oracle.beans.Product;
import com.oracle.dao.ProductDAO;
import com.oracle.factory.DAOFactory;
import com.oracle.service.ProductService;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductDAO productDAO = DAOFactory.getProductDAO();

    @Override
    public void addProduct(Product product) {
        productDAO.addProduct(product);
    }

    @Override
    public void updateProduct(Product product) {
        productDAO.updateProduct(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        productDAO.deleteProduct(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    @Override
    public Product getProductById(Long productId) {
        return productDAO.getProductById(productId);
    }
}
