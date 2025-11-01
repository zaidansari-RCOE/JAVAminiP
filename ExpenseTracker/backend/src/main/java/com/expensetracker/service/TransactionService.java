package com.expensetracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import com.expensetracker.model.Transaction;
import com.expensetracker.repository.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository repo;

    public List<Transaction> getAll() { return repo.findAll(); }

    public Transaction add(Transaction t) { return repo.save(t); }

    public Transaction update(int id, Transaction newT) {
        return repo.findById(id).map(t -> {
            t.setDescription(newT.getDescription());
            t.setCategory(newT.getCategory());
            t.setAmount(newT.getAmount());
            t.setDate(newT.getDate());
            t.setType(newT.getType());
            return repo.save(t);
        }).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public void delete(int id) { repo.deleteById(id); }
}
