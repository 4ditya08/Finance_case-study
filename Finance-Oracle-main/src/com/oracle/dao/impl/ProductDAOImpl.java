package com.oracle.dao.impl;

import com.oracle.beans.Product;
import com.oracle.dao.ProductDAO;
import jakarta.persistence.*;

import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Finance");

    @Override
    public void addProduct(Product product) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(product);
            tx.commit();
            System.out.println("✅ Product added successfully!");
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void updateProduct(Product product) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(product);
            tx.commit();
            System.out.println("✅ Product updated successfully!");
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteProduct(Long productId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Product p = em.find(Product.class, productId);
            if (p != null) {
                em.remove(p);
                System.out.println("✅ Product deleted!");
            } else {
                System.out.println("❌ Product not found!");
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Product> getAllProducts() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Product p ORDER BY p.productId", Product.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Product getProductById(Long productId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Product.class, productId);
        } finally {
            em.close();
        }
    }
}
