package com.oracle.service.impl;

import com.oracle.beans.User;
import com.oracle.beans.EMICard;
import com.oracle.beans.Product;
import com.oracle.beans.Purchase;
import com.oracle.dao.PurchaseDAO;
import com.oracle.factory.DAOFactory;
import com.oracle.service.PurchaseService;
import java.util.List;

public class PurchaseServiceImpl implements PurchaseService {
    private PurchaseDAO purchaseDAO = DAOFactory.getPurchaseDAO();

    @Override
    public void createPurchase(User user, EMICard card, List<Product> products, int tenureMonths) {
        purchaseDAO.createPurchase(user, card, products, tenureMonths);
    }

    @Override
    public List<Purchase> getOngoingPurchasesByUser(Long userId) {
        return purchaseDAO.getOngoingPurchasesByUser(userId);
    }

    @Override
    public void payNextEMI(Purchase purchase) {
        purchaseDAO.payNextEMI(purchase);
    }
}
