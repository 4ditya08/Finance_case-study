package com.oracle.dao.impl;

import com.oracle.beans.EMICard;
import com.oracle.beans.User;
import com.oracle.dao.EMICardDAO;
import jakarta.persistence.*;

import java.util.List;

public class EMICardDAOImpl implements EMICardDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Finance");

    @Override
    public void approveUserAndCard(Long userId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            User user = em.find(User.class, userId);
            if (user != null && "Pending".equalsIgnoreCase(user.getStatus())) {
                user.setStatus("Active");

                List<EMICard> cards = em
                        .createQuery("SELECT c FROM EMICard c WHERE c.user.userId = :uid", EMICard.class)
                        .setParameter("uid", userId)
                        .getResultList();

                if (!cards.isEmpty()) {
                    EMICard card = cards.get(0);
                    card.setStatus("Active");
                }

                System.out.println("✅ User and EMI Card approved successfully.");
            } else {
                System.out.println("❌ User not found or already active.");
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void listPendingUsers() {
        EntityManager em = emf.createEntityManager();
        try {
            List<User> users = em.createQuery(
                    "SELECT u FROM User u WHERE u.status = 'Pending'", User.class
            ).getResultList();

            if (users.isEmpty()) {
                System.out.println("No pending users found.");
            } else {
                System.out.println("📋 Pending Users:");
                for (User u : users) {
                    System.out.println(u.getUserId() + " - " + u.getName() + " (" + u.getUsername() + ")");
                }
            }
        } finally {
            em.close();
        }
    }

    @Override
    public EMICard getCardByUserId(Long userId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT c FROM EMICard c WHERE c.user.userId = :uid", EMICard.class)
                    .setParameter("uid", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
