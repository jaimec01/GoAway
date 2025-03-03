package com.tfg.GoAway.user.transaction.application.find_all_tenant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.tfg.GoAway.user.transaction.domain.Transaction;
import com.tfg.GoAway.user.transaction.domain.TransactionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionGetByTenant {

    private final TransactionRepository transactionRepository;

    public List<TransactionGetTenantResponse> execute(String tenantEmail) {
        List<Transaction> transactions = transactionRepository.findByTenantEmail(tenantEmail);

        return transactions.stream()
                .map(TransactionGetTenantMapper::toResponse)
                .collect(Collectors.toList());
    }
}
