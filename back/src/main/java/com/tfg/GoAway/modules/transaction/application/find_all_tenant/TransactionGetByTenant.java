package com.tfg.GoAway.modules.transaction.application.find_all_tenant;

import com.tfg.GoAway.modules.transaction.domain.Transaction;
import com.tfg.GoAway.modules.transaction.domain.TransactionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionGetByTenant {

    private final TransactionRepository transactionRepository;

    public List<TransactionGetTenantResponse> execute(String tenantEmail) {
        List<Transaction> transactions = transactionRepository.findByTenantEmail(tenantEmail);

        if (transactions.isEmpty()) {
            throw new IllegalArgumentException("Aún no tienes transacciones.");
        }
        
        return transactions.stream()
                .map(TransactionGetTenantMapper::toResponse)
                .collect(Collectors.toList());
    }
}
