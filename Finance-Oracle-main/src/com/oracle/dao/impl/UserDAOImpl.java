package com.oracle.dao.impl;

import com.oracle.beans.User;
import com.oracle.beans.EMICard;
import com.oracle.dao.UserDAO;
import jakarta.persistence.*;

import java.time.LocalDate;

public class UserDAOImpl implements UserDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Finance");

    @Override
    public User login(String username, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.username = :username AND u.password = :password AND u.status = 'Active'",
                User.class
            );
            query.setParameter("username", username);
            query.setParameter("password", password);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public void registerNewUser(User user, String cardType) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Check for duplicates before inserting
            long count = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.username = :username OR u.email = :email OR u.phoneNo = :phone",
                Long.class)
                .setParameter("username", user.getUsername())
                .setParameter("email", user.getEmail())
                .setParameter("phone", user.getPhoneNo())
                .getSingleResult();

            if (count > 0) {
                System.out.println("❌ Username, email or phone number already exists. Registration failed.");
                tx.rollback();
                return;
            }

            // User starts as 'Pending' until admin approval
            user.setStatus("Pending");
            em.persist(user);

            // Create EMI Card for this user
            EMICard card = new EMICard();
            card.setUser(user);
            card.setCardType(cardType);

            // Generate unique card number
            card.setCardNumber(generateCardNumber(cardType));

            // Card limits & fees based on type
            if ("Gold".equalsIgnoreCase(cardType)) {
                card.setCardLimit(100000);
                card.setJoiningFee(500);
            } else { // Titanium
                card.setCardLimit(200000);
                card.setJoiningFee(1000);
            }

            card.setRemainingLimit(card.getCardLimit());
            card.setValidTill(LocalDate.now().plusYears(3));
            card.setStatus("Pending");

            em.persist(card);

            tx.commit();
            System.out.println("✅ User registered with pending EMI card (Card No: " + card.getCardNumber() + "). Awaiting Admin approval.");

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * Generates a unique card number.
     * Gold cards → start with 4900, Titanium → start with 5500.
     */
    private String generateCardNumber(String cardType) {
        StringBuilder sb = new StringBuilder();
        if ("Gold".equalsIgnoreCase(cardType)) {
            sb.append("4900");
        } else {
            sb.append("5500");
        }
        for (int i = 0; i < 12; i++) {
            sb.append((int) (Math.random() * 10));
        }
        return sb.toString();
    }
}
