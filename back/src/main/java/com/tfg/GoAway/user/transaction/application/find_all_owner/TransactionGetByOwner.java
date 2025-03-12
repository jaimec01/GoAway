package com.tfg.GoAway.user.transaction.application.find_all_owner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.tfg.GoAway.user.transaction.domain.Transaction;
import com.tfg.GoAway.user.transaction.domain.TransactionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionGetByOwner {

    private final TransactionRepository transactionRepository;

    public List<TransactionGetOwnerResponse> execute(String ownerEmail, String order) {
        List<Transaction> transactions;
        if ("asc".equalsIgnoreCase(order)) {
            transactions = transactionRepository.findByOwnerEmailOrderByUpdatedAtAsc(ownerEmail);
        } else {
            // Por defecto, orden descendente
            transactions = transactionRepository.findByOwnerEmailOrderByUpdatedAtDesc(ownerEmail);
        }
        return transactions.stream()
                .map(TransactionGetOwnerMapper::toResponse)
                .collect(Collectors.toList());
    }
}