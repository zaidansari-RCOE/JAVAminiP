package com.expensetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.expensetracker.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
