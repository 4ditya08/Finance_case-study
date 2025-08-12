package com.oracle.dao;

import com.oracle.beans.User;
import com.oracle.beans.EMICard;
import com.oracle.beans.Product;

import java.util.List;

public interface PurchaseDAO {
    void createPurchase(User user, EMICard card, List<Product> products, int tenureMonths);
}
