package com.oracle.dao.impl;

import com.oracle.beans.Transaction;
import com.oracle.dao.TransactionDAO;
import jakarta.persistence.*;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Finance");

    @Override
    public List<Transaction> getTransactionsByUserId(Long userId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                "SELECT t FROM Transaction t " +
                "WHERE t.user.userId = :uid AND t.transactionType = 'Product Purchase' " +
                "ORDER BY t.transactionDate DESC",
                Transaction.class)
                .setParameter("uid", userId)
                .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Transaction> getAllTransactions() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                "SELECT t FROM Transaction t ORDER BY t.transactionDate DESC",
                Transaction.class)
                .getResultList();
        } finally {
            em.close();
        }
    }
}
