package com.oracle;

import com.oracle.beans.Admin;
import com.oracle.beans.User;
import com.oracle.beans.EMICard;
import com.oracle.beans.Product;
import com.oracle.beans.Transaction;
import com.oracle.beans.PurchaseItem;
import com.oracle.dao.AdminDAO;
import com.oracle.dao.UserDAO;
import com.oracle.dao.EMICardDAO;
import com.oracle.dao.ProductDAO;
import com.oracle.dao.PurchaseDAO;
import com.oracle.dao.TransactionDAO;
import com.oracle.dao.impl.AdminDAOImpl;
import com.oracle.dao.impl.UserDAOImpl;
import com.oracle.dao.impl.EMICardDAOImpl;
import com.oracle.dao.impl.ProductDAOImpl;
import com.oracle.dao.impl.PurchaseDAOImpl;
import com.oracle.dao.impl.TransactionDAOImpl;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class FinanceMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AdminDAO adminDAO = new AdminDAOImpl();
        UserDAO userDAO = new UserDAOImpl();
        EMICardDAO emicardDAO = new EMICardDAOImpl();
        ProductDAO productDAO = new ProductDAOImpl();
        PurchaseDAO purchaseDAO = new PurchaseDAOImpl();
        TransactionDAO transactionDAO = new TransactionDAOImpl();

        boolean running = true;

        while (running) {
            System.out.println("\n==== Finance Management System ====");
            System.out.println("1. Login as Admin");
            System.out.println("2. Login as User");
            System.out.println("3. New User Registration");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                // ================== ADMIN LOGIN ==================
                case 1 -> {
                    System.out.print("Enter username: ");
                    String adminUsername = scanner.nextLine().trim();
                    System.out.print("Enter password: ");
                    String adminPassword = scanner.nextLine().trim();

                    Admin admin = adminDAO.login(adminUsername, adminPassword);
                    if (admin != null) {
                        System.out.println("✅ Welcome Admin: " + admin.getName());
                        boolean adminRunning = true;
                        while (adminRunning) {
                            System.out.println("\n==== Admin Menu ====");
                            System.out.println("1. View Pending Users");
                            System.out.println("2. Approve User & EMI Card");
                            System.out.println("3. Manage Products");
                            System.out.println("4. View All Transactions");
                            System.out.println("5. Logout");
                            System.out.print("Choose: ");
                            int adminChoice = scanner.nextInt();
                            scanner.nextLine();

                            switch (adminChoice) {
                                case 1 -> emicardDAO.listPendingUsers();
                                case 2 -> {
                                    System.out.print("Enter User ID to approve: ");
                                    long uid = scanner.nextLong();
                                    scanner.nextLine();
                                    emicardDAO.approveUserAndCard(uid);
                                }
                                case 3 -> { // Product Management
                                    boolean managingProducts = true;
                                    while (managingProducts) {
                                        System.out.println("\n==== Product Management ====");
                                        System.out.println("1. Add Product");
                                        System.out.println("2. Update Product");
                                        System.out.println("3. Delete Product");
                                        System.out.println("4. View All Products");
                                        System.out.println("5. Back to Admin Menu");
                                        System.out.print("Choose: ");
                                        int pc = scanner.nextInt();
                                        scanner.nextLine();

                                        switch (pc) {
                                            case 1 -> {
                                                Product p = new Product();
                                                System.out.print("Enter Name: ");
                                                p.setName(scanner.nextLine());
                                                System.out.print("Enter Description: ");
                                                p.setDescription(scanner.nextLine());
                                                System.out.print("Enter Price: ");
                                                p.setPrice(scanner.nextDouble());
                                                scanner.nextLine();
                                                System.out.print("Enter Category: ");
                                                p.setCategory(scanner.nextLine());
                                                productDAO.addProduct(p);
                                            }
                                            case 2 -> {
                                                System.out.print("Enter Product ID to update: ");
                                                long pid = scanner.nextLong();
                                                scanner.nextLine();
                                                Product existing = productDAO.getProductById(pid);
                                                if (existing != null) {
                                                    System.out.print("Enter New Name: ");
                                                    existing.setName(scanner.nextLine());
                                                    System.out.print("Enter New Description: ");
                                                    existing.setDescription(scanner.nextLine());
                                                    System.out.print("Enter New Price: ");
                                                    existing.setPrice(scanner.nextDouble());
                                                    scanner.nextLine();
                                                    System.out.print("Enter New Category: ");
                                                    existing.setCategory(scanner.nextLine());
                                                    productDAO.updateProduct(existing);
                                                } else {
                                                    System.out.println("❌ Product not found!");
                                                }
                                            }
                                            case 3 -> {
                                                System.out.print("Enter Product ID to delete: ");
                                                long pid = scanner.nextLong();
                                                scanner.nextLine();
                                                productDAO.deleteProduct(pid);
                                            }
                                            case 4 -> {
                                                List<Product> list = productDAO.getAllProducts();
                                                if (list.isEmpty()) {
                                                    System.out.println("No products found.");
                                                } else {
                                                    System.out.println("\n--- Product Catalog ---");
                                                    for (Product pr : list) {
                                                        System.out.printf("%d. %s | %s | ₹%.2f\n",
                                                                pr.getProductId(),
                                                                pr.getName(),
                                                                pr.getCategory(),
                                                                pr.getPrice());
                                                        System.out.println("   " + pr.getDescription());
                                                    }
                                                }
                                            }
                                            case 5 -> managingProducts = false;
                                            default -> System.out.println("❌ Invalid choice.");
                                        }
                                    }
                                }
                                case 4 -> { // View all transactions
                                    List<Transaction> txns = transactionDAO.getAllTransactions();
                                    if (txns.isEmpty()) {
                                        System.out.println("No transactions found.");
                                    } else {
                                        for (Transaction t : txns) {
                                            System.out.printf("Txn ID: %d | User: %s | Type: %s | Amount: ₹%.2f | Date: %s\n",
                                                    t.getTransactionId(),
                                                    t.getUser().getName(),
                                                    t.getTransactionType(),
                                                    t.getAmount(),
                                                    t.getTransactionDate());
                                        }
                                    }
                                }
                                case 5 -> adminRunning = false;
                                default -> System.out.println("❌ Invalid choice.");
                            }
                        }
                    } else {
                        System.out.println("❌ Invalid Admin Credentials");
                    }
                }

                // ================== USER LOGIN ==================
                case 2 -> {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine().trim();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine().trim();

                    User user = userDAO.login(username, password);
                    if (user != null) {
                        System.out.println("✅ Welcome User: " + user.getName());

                        boolean userRunning = true;
                        while (userRunning) {
                            System.out.println("\n==== User Menu ====");
                            System.out.println("1. View Profile");
                            System.out.println("2. View EMI Card Details");
                            System.out.println("3. View Product Catalog");
                            System.out.println("4. Buy Product");
                            System.out.println("5. View My Purchases");
                            System.out.println("6. Logout");
                            System.out.print("Choose: ");
                            int userChoice = scanner.nextInt();
                            scanner.nextLine();

                            switch (userChoice) {
                                case 1 -> {
                                    System.out.println("\n--- Profile ---");
                                    System.out.println("Name: " + user.getName());
                                    System.out.println("DOB: " + user.getDob());
                                    System.out.println("Email: " + user.getEmail());
                                    System.out.println("Phone: " + user.getPhoneNo());
                                    System.out.println("Address: " + user.getAddress());
                                    System.out.println("Status: " + user.getStatus());
                                }
                                case 2 -> {
                                    EMICard card = emicardDAO.getCardByUserId(user.getUserId());
                                    if (card != null) {
                                        System.out.println("\n--- EMI Card Details ---");
                                        System.out.println("Card Number: " + card.getCardNumber());
                                        System.out.println("Card Type: " + card.getCardType());
                                        System.out.println("Limit: " + card.getCardLimit());
                                        System.out.println("Remaining Limit: " + card.getRemainingLimit());
                                        System.out.println("Joining Fee: " + card.getJoiningFee());
                                        System.out.println("Valid Till: " + card.getValidTill());
                                        System.out.println("Status: " + card.getStatus());
                                    } else {
                                        System.out.println("❌ No EMI card found.");
                                    }
                                }
                                case 3 -> {
                                    List<Product> list = productDAO.getAllProducts();
                                    if (list.isEmpty()) {
                                        System.out.println("No products available.");
                                    } else {
                                        System.out.println("\n--- Product Catalog ---");
                                        for (Product pr : list) {
                                            System.out.printf("%d. %s | %s | ₹%.2f\n",
                                                    pr.getProductId(),
                                                    pr.getName(),
                                                    pr.getCategory(),
                                                    pr.getPrice());
                                            System.out.println("   " + pr.getDescription());
                                        }
                                    }
                                }
                                case 4 -> {
                                    EMICard card = emicardDAO.getCardByUserId(user.getUserId());
                                    if (card == null || !"Active".equalsIgnoreCase(card.getStatus())) {
                                        System.out.println("❌ You do not have an active EMI card.");
                                        break;
                                    }
                                    List<Product> prods = productDAO.getAllProducts();
                                    if (prods.isEmpty()) {
                                        System.out.println("❌ No products available.");
                                        break;
                                    }
                                    System.out.println("\n--- Product Catalog ---");
                                    for (Product p : prods) {
                                        System.out.printf("%d. %s | %s | ₹%.2f\n",
                                                p.getProductId(), p.getName(), p.getCategory(), p.getPrice());
                                        System.out.println("   " + p.getDescription());
                                    }
                                    System.out.print("Enter Product ID to buy: ");
                                    long pid = scanner.nextLong();
                                    scanner.nextLine();
                                    Product selected = productDAO.getProductById(pid);
                                    if (selected == null) {
                                        System.out.println("❌ Invalid product ID.");
                                        break;
                                    }
                                    System.out.print("Enter EMI Tenure (3/6/9/12 months): ");
                                    int tenure = scanner.nextInt();
                                    scanner.nextLine();
                                    if (!(tenure == 3 || tenure == 6 || tenure == 9 || tenure == 12)) {
                                        System.out.println("❌ Invalid tenure.");
                                        break;
                                    }
                                    System.out.print("Confirm purchase of " + selected.getName() +
                                            " for ₹" + selected.getPrice() + "? (y/n): ");
                                    String confirm = scanner.nextLine();
                                    if (confirm.equalsIgnoreCase("y")) {
                                        purchaseDAO.createPurchase(user, card, List.of(selected), tenure);
                                    } else {
                                        System.out.println("Purchase cancelled.");
                                    }
                                }
                                case 5 -> { // View my purchases
                                    List<Transaction> txns = transactionDAO.getTransactionsByUserId(user.getUserId());
                                    if (txns.isEmpty()) {
                                        System.out.println("No purchases found.");
                                    } else {
                                        for (Transaction t : txns) {
                                            System.out.printf("Purchase ID: %d | Amount: ₹%.2f | Date: %s\n",
                                                    t.getPurchase().getPurchaseId(),
                                                    t.getAmount(),
                                                    t.getTransactionDate());
                                            if (t.getPurchase().getItems() != null) {
                                                for (PurchaseItem item : t.getPurchase().getItems()) {
                                                    System.out.printf("   - %s (₹%.2f)\n",
                                                            item.getProduct().getName(),
                                                            item.getPrice());
                                                }
                                            }
                                        }
                                    }
                                }
                                case 6 -> userRunning = false;
                                default -> System.out.println("❌ Invalid choice.");
                            }
                        }
                    } else {
                        System.out.println("❌ Invalid User Credentials or Account Inactive");
                    }
                }

                // ================== NEW USER REGISTRATION ==================
                case 3 -> {
                    User newUser = new User();
                    System.out.print("Enter Name: ");
                    newUser.setName(scanner.nextLine().trim());
                    System.out.print("Enter DOB (yyyy-mm-dd): ");
                    newUser.setDob(Date.valueOf(scanner.nextLine().trim()));
                    System.out.print("Enter Email: ");
                    newUser.setEmail(scanner.nextLine().trim());
                    System.out.print("Enter Phone: ");
                    newUser.setPhoneNo(scanner.nextLine().trim());
                    System.out.print("Enter Address: ");
                    newUser.setAddress(scanner.nextLine().trim());
                    System.out.print("Choose Username: ");
                    newUser.setUsername(scanner.nextLine().trim());
                    System.out.print("Choose Password: ");
                    newUser.setPassword(scanner.nextLine().trim());
                    System.out.print("Select EMI Card Type (Gold/Titanium): ");
                    String type = scanner.nextLine().trim();
                    userDAO.registerNewUser(newUser, type);
                }

                // ================== EXIT ==================
                case 4 -> {
                    System.out.println("👋 Exiting... Goodbye!");
                    running = false;
                }

                default -> System.out.println("❌ Invalid choice.");
            }
        }
        scanner.close();
    }
}
