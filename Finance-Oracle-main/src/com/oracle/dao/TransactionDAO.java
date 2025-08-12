package com.oracle.dao;

import com.oracle.beans.Transaction;
import java.util.List;

public interface TransactionDAO {
    List<Transaction> getTransactionsByUserId(Long userId);
    List<Transaction> getAllTransactions(); // for admin
}
