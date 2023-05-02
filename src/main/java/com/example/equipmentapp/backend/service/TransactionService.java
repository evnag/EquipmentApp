package com.example.equipmentapp.backend.service;

import com.example.equipmentapp.backend.entity.Transaction;
import com.example.equipmentapp.backend.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public List<Transaction> findAllByUnitId(Long unitId) {
        return transactionRepository.findAllByUnitId(unitId);
    }

    public long count() {
        return transactionRepository.count();
    }

    public void save(Transaction transaction) {
        if (transaction == null) {
            return;
        }
        transactionRepository.save(transaction);
    }

    public void delete(Transaction transaction) {
        transactionRepository.delete(transaction);
    }
}
