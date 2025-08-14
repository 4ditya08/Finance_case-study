package com.oracle.service;

import com.oracle.beans.Transaction;
import java.util.List;

public interface TransactionService {
    List<Transaction> getTransactionsByUserId(Long userId);
    List<Transaction> getAllTransactions();
}
