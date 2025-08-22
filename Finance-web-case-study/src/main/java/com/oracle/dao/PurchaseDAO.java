package com.oracle.dao;

import com.oracle.beans.User;
import com.oracle.beans.EMICard;
import com.oracle.beans.Product;
import com.oracle.beans.Purchase;

import java.util.List;

public interface PurchaseDAO {
    void createPurchase(User user, EMICard card, List<Product> products, int tenureMonths);
    
    List<Purchase> getOngoingPurchasesByUser(Long userId);
    void payNextEMI(Purchase purchase);

}
