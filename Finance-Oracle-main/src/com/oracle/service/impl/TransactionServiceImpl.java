package com.oracle.service.impl;

import com.oracle.beans.Transaction;
import com.oracle.dao.TransactionDAO;
import com.oracle.factory.DAOFactory;
import com.oracle.service.TransactionService;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    private TransactionDAO transactionDAO = DAOFactory.getTransactionDAO();

    @Override
    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionDAO.getTransactionsByUserId(userId);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionDAO.getAllTransactions();
    }
}
