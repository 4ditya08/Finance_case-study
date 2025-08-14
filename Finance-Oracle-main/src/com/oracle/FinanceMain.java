package com.oracle;

import com.oracle.beans.*;
import com.oracle.factory.ServiceFactory;
import com.oracle.service.*;


import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class FinanceMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // === Service layer instances ===
        AdminService adminService = ServiceFactory.getAdminService();
        UserService userService = ServiceFactory.getUserService();
        EMICardService emiCardService = ServiceFactory.getEMICardService();
        ProductService productService = ServiceFactory.getProductService();
        PurchaseService purchaseService = ServiceFactory.getPurchaseService();
        TransactionService transactionService = ServiceFactory.getTransactionService();

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

                /** ========== ADMIN LOGIN ========== **/
                case 1 -> {
                    System.out.print("Enter username: ");
                    String adminUsername = scanner.nextLine().trim();
                    System.out.print("Enter password: ");
                    String adminPassword = scanner.nextLine().trim();

                    if (adminService.login(adminUsername, adminPassword)) {
                        System.out.println("✅ Welcome Admin!");
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
                                case 1 -> adminService.listPendingUsers();
                                case 2 -> {
                                    System.out.print("Enter User ID to approve: ");
                                    long uid = scanner.nextLong();
                                    scanner.nextLine();
                                    adminService.approveUserAndCard(uid);
                                }
                                case 3 -> {
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
                                                productService.addProduct(p);
                                            }
                                            case 2 -> {
                                                System.out.print("Enter Product ID to update: ");
                                                long pid = scanner.nextLong();
                                                scanner.nextLine();
                                                Product existing = productService.getProductById(pid);
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
                                                    productService.updateProduct(existing);
                                                } else {
                                                    System.out.println("❌ Product not found!");
                                                }
                                            }
                                            case 3 -> {
                                                System.out.print("Enter Product ID to delete: ");
                                                long pid = scanner.nextLong();
                                                scanner.nextLine();
                                                productService.deleteProduct(pid);
                                            }
                                            case 4 -> {
                                                List<Product> list = productService.getAllProducts();
                                                if (list.isEmpty()) System.out.println("No products found.");
                                                else list.forEach(pr -> {
                                                    System.out.printf("%d. %s | %s | ₹%.2f\n", pr.getProductId(), pr.getName(),
                                                            pr.getCategory(), pr.getPrice());
                                                    System.out.println("   " + pr.getDescription());
                                                });
                                            }
                                            case 5 -> managingProducts = false;
                                            default -> System.out.println("❌ Invalid choice.");
                                        }
                                    }
                                }
                                case 4 -> {
                                    List<Transaction> txns = transactionService.getAllTransactions();
                                    if (txns.isEmpty()) System.out.println("No transactions found.");
                                    else txns.forEach(t -> System.out.printf(
                                            "Txn ID: %d | User: %s | Type: %s | Amount: ₹%.2f | Date: %s\n",
                                            t.getTransactionId(), t.getUser().getName(),
                                            t.getTransactionType(), t.getAmount(), t.getTransactionDate()));
                                }
                                case 5 -> adminRunning = false;
                                default -> System.out.println("❌ Invalid choice.");
                            }
                        }
                    } else {
                        System.out.println("❌ Invalid Admin Credentials");
                    }
                }

                /** ========== USER LOGIN ========== **/
                case 2 -> {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine().trim();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine().trim();

                    User user = userService.login(username, password);
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
                            System.out.println("6. Pay EMI");
                            System.out.println("7. Logout");
                            System.out.print("Choose: ");
                            int userChoice = scanner.nextInt();
                            scanner.nextLine();

                            switch (userChoice) {
                                case 1 -> {
                                    System.out.printf("\nName: %s\nDOB: %s\nEmail: %s\nPhone: %s\nAddress: %s\nStatus: %s\n",
                                            user.getName(), user.getDob(), user.getEmail(),
                                            user.getPhoneNo(), user.getAddress(), user.getStatus());
                                }
                                case 2 -> {
                                    EMICard card = emiCardService.getCardByUserId(user.getUserId());
                                    if (card != null) {
                                        System.out.printf("\nCard No: %s\nType: %s\nLimit: ₹%.2f\nRemaining: ₹%.2f\nJoining Fee: ₹%.2f\nValid Till: %s\nStatus: %s\n",
                                                card.getCardNumber(), card.getCardType(), card.getCardLimit(),
                                                card.getRemainingLimit(), card.getJoiningFee(),
                                                card.getValidTill(), card.getStatus());
                                    } else System.out.println("❌ No EMI card found.");
                                }
                                case 3 -> {
                                    List<Product> list = productService.getAllProducts();
                                    if (list.isEmpty()) System.out.println("No products available.");
                                    else list.forEach(pr -> {
                                        System.out.printf("%d. %s | %s | ₹%.2f\n",
                                                pr.getProductId(), pr.getName(), pr.getCategory(), pr.getPrice());
                                        System.out.println("   " + pr.getDescription());
                                    });
                                }
                                case 4 -> {
                                    EMICard card = emiCardService.getCardByUserId(user.getUserId());
                                    if (card == null || !"Active".equalsIgnoreCase(card.getStatus())) {
                                        System.out.println("❌ You do not have an active EMI card.");
                                        break;
                                    }
                                    List<Product> prods = productService.getAllProducts();
                                    if (prods.isEmpty()) {
                                        System.out.println("❌ No products available.");
                                        break;
                                    }
                                    prods.forEach(p -> {
                                        System.out.printf("%d. %s | %s | ₹%.2f\n", p.getProductId(), p.getName(),
                                                p.getCategory(), p.getPrice());
                                        System.out.println("   " + p.getDescription());
                                    });
                                    System.out.print("Enter Product ID: ");
                                    long pid = scanner.nextLong();
                                    scanner.nextLine();
                                    Product selected = productService.getProductById(pid);
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
                                    System.out.print("Confirm purchase? (y/n): ");
                                    if (scanner.nextLine().equalsIgnoreCase("y")) {
                                        purchaseService.createPurchase(user, card, List.of(selected), tenure);
                                    } else System.out.println("Purchase cancelled.");
                                }
                                case 5 -> {
                                    List<Transaction> txns = transactionService.getTransactionsByUserId(user.getUserId());
                                    if (txns.isEmpty()) System.out.println("No purchases found.");
                                    else txns.forEach(t -> {
                                        System.out.printf("\nPurchase ID: %d | Amount: ₹%.2f | Date: %s\n",
                                                t.getPurchase().getPurchaseId(), t.getAmount(), t.getTransactionDate());
                                        if (t.getPurchase().getItems() != null) {
                                            for (PurchaseItem item : t.getPurchase().getItems()) {
                                                System.out.printf("   - %s (₹%.2f)\n", item.getProduct().getName(), item.getPrice());
                                            }
                                        }
                                    });
                                }
                                case 6 -> {
                                    List<Purchase> ongoing = purchaseService.getOngoingPurchasesByUser(user.getUserId());
                                    if (ongoing.isEmpty()) {
                                        System.out.println("No ongoing purchases.");
                                    } else {
                                        ongoing.forEach(p -> {
                                            double emiAmt = (p.getTotalAmount() + p.getProcessingFee()) / p.getTenureMonths();
                                            System.out.printf("Purchase ID: %d | EMI: ₹%.2f | Paid: %d/%d | Status: %s\n",
                                                    p.getPurchaseId(), emiAmt, p.getPaidEmis(), p.getTenureMonths(), p.getStatus());
                                        });
                                        System.out.print("Enter Purchase ID to pay EMI: ");
                                        long pid = scanner.nextLong();
                                        scanner.nextLine();
                                        ongoing.stream().filter(p -> p.getPurchaseId() == pid).findFirst()
                                                .ifPresentOrElse(purchaseService::payNextEMI,
                                                        () -> System.out.println("❌ Invalid Purchase ID."));
                                    }
                                }
                                case 7 -> userRunning = false;
                                default -> System.out.println("❌ Invalid choice.");
                            }
                        }
                    } else {
                        System.out.println("❌ Invalid credentials or inactive account.");
                    }
                }

                /** ========== NEW USER REGISTRATION ========== **/
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
                    userService.registerNewUser(newUser, type);
                }

                /** ========== EXIT ========== **/
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
