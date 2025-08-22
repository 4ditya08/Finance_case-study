package com.oracle.dao.impl;

import com.oracle.beans.User;
import com.oracle.dao.UserDAO;
import jakarta.persistence.*;

public class UserDAOImpl implements UserDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Finance-web-case-study");

    @Override
    public User login(String username, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                "SELECT u FROM User u WHERE u.username = :uname AND u.password = :pwd AND u.status = 'Active'",
                User.class)
                .setParameter("uname", username)
                .setParameter("pwd", password)
                .getResultStream()
                .findFirst()
                .orElse(null);
        } finally {
            em.close();
        }
    }

    @Override
    public void registerNewUser(User user, String cardType) {
        // existing registration logic
    }

    @Override
    public User findUserById(Long userId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(User.class, userId);
        } finally {
            em.close();
        }
    }
}
