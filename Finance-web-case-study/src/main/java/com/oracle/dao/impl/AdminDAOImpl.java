//package com.oracle.dao.impl;

//
//import com.oracle.beans.Admin;
//import com.oracle.dao.AdminDAO;
//import jakarta.persistence.*;
//
//import java.util.List;
//
//public class AdminDAOImpl implements AdminDAO {
//
//    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Finance");
//
//    @Override
//    public Admin login(String username, String password) {
//        EntityManager em = emf.createEntityManager();
//
//        try {
//            System.out.println("🔍 Trying to login with username: " + username + ", password: " + password);
//
//            TypedQuery<Admin> query = em.createQuery(
//                "SELECT a FROM Admin a WHERE TRIM(a.username) = :username AND TRIM(a.password) = :password",
//                Admin.class
//            );
//            query.setParameter("username", username);
//            query.setParameter("password", password);
//
//            // ⚠️ Instead of getSingleResult (which throws exception), use getResultList
//            List<Admin> results = query.getResultList();
//            System.out.println("📊 Rows matched: " + results.size());
//
//            if (!results.isEmpty()) {
//                System.out.println("✅ Login successful! Welcome " + results.get(0).getName());
//                return results.get(0);
//            } else {
//                System.out.println("❌ Invalid Admin Credentials");
//                return null;
//            }
//
//        } finally {
//            em.close();
//        }
//    }
//}


package com.oracle.dao.impl;

import com.oracle.beans.Admin;
import com.oracle.beans.User;
import com.oracle.dao.AdminDAO;
import jakarta.persistence.*;

import java.util.List;

public class AdminDAOImpl implements AdminDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Finance-web-case-study");

    @Override
    public Admin login(String username, String password) {
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("🔍 Trying native SQL login with username: " + username + ", password: " + password);

            Query nativeQuery = em.createNativeQuery(
                "SELECT * FROM ADMINS WHERE TRIM(USERNAME) = ? AND TRIM(PASSWORD) = ?", Admin.class
            );
            nativeQuery.setParameter(1, username.trim());
            nativeQuery.setParameter(2, password.trim());

            List<Admin> results = nativeQuery.getResultList();
            System.out.println("📊 Native SQL matched rows: " + results.size());

            if (!results.isEmpty()) {
                return results.get(0);
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
    
    public void printAllUsers() {
        EntityManager em = emf.createEntityManager();
        try {
            List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
            if (users.isEmpty()) {
                System.out.println("❌ No users found in the database.");
            } else {
                System.out.println("📋 Users found in database:");
                for (User user : users) {
                    System.out.println("👤 Username: " + user.getUsername() +
                                       " | Password: " + user.getPassword() +
                                       " | Status: " + user.getStatus());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
