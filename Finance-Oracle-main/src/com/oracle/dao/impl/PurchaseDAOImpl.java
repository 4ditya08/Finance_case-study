package com.oracle.dao.impl;

import com.oracle.beans.*;
import com.oracle.dao.PurchaseDAO;
import jakarta.persistence.*;

import java.util.List;

public class PurchaseDAOImpl implements PurchaseDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Finance");

    @Override
    public void createPurchase(User user, EMICard card, List<Product> products, int tenureMonths) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // 1. Calculate total amount
            double totalAmount = 0;
            for (Product p : products) {
                totalAmount += p.getPrice(); // quantity = 1 for now
            }

            // 2. Processing fee (2%)
            double processingFee = totalAmount * 0.02;

            // 3. Check available limit
            if (totalAmount + processingFee > card.getRemainingLimit()) {
                System.out.println("❌ Not enough card limit. Purchase denied.");
                tx.rollback();
                return;
            }

            // 4. Create Purchase record
            Purchase purchase = new Purchase();
            purchase.setUser(user);
            purchase.setCard(card);
            purchase.setTenureMonths(tenureMonths);
            purchase.setTotalAmount(totalAmount);
            purchase.setProcessingFee(processingFee);
            purchase.setStatus("Ongoing");
            em.persist(purchase);

            // 5. Create PurchaseItem records
            for (Product p : products) {
                PurchaseItem item = new PurchaseItem();
                item.setPurchase(purchase);
                item.setProduct(p);
                item.setQuantity(1);
                item.setPrice(p.getPrice());
                em.persist(item);
            }

            // 6. Deduct limit from EMI card
            card.setRemainingLimit(card.getRemainingLimit() - (totalAmount + processingFee));
            em.merge(card);

            // 7. Add transaction for the product purchase
            Transaction purchaseTxn = new Transaction();
            purchaseTxn.setUser(user);
            purchaseTxn.setPurchase(purchase);
            purchaseTxn.setAmount(totalAmount);
            purchaseTxn.setTransactionType("Product Purchase");
            em.persist(purchaseTxn);

            // 8. Add transaction for processing fee
            Transaction feeTxn = new Transaction();
            feeTxn.setUser(user);
            feeTxn.setPurchase(purchase);
            feeTxn.setAmount(processingFee);
            feeTxn.setTransactionType("Processing Fee");
            em.persist(feeTxn);

            tx.commit();
            System.out.println("✅ Purchase successful! Total: ₹" + totalAmount +
                    " | EMI: ₹" + String.format("%.2f", (totalAmount + processingFee) / tenureMonths) +
                    " for " + tenureMonths + " months.");

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
