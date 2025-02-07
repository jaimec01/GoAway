package com.tfg.GoAway.modules.transaction.application.find_all_owner;

import com.tfg.GoAway.modules.transaction.domain.Transaction;
import com.tfg.GoAway.modules.transaction.domain.TransactionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionGetByOwner {

    private final TransactionRepository transactionRepository;

    public List<TransactionGetOwnerResponse> execute(String ownerEmail) {
        List<Transaction> transactions = transactionRepository.findByOwnerEmail(ownerEmail);

        if (transactions.isEmpty()) {
            throw new IllegalArgumentException("Aún no tienes transacciones.");
        }
        
        return transactions.stream()
                .map(TransactionGetOwnerMapper::toResponse)
                .collect(Collectors.toList());
    }
}
