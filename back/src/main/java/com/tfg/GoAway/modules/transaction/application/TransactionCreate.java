package com.tfg.GoAway.modules.transaction.application;

import com.tfg.GoAway.modules.transaction.domain.Transaction;
import com.tfg.GoAway.modules.transaction.domain.TransactionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionCreate {

    private final TransactionRepository transactionRepository;

    public TransactionCreateResponse execute(TransactionCreateRecord record) {

        Transaction transaction = TransactionCreateMapper.toDomain(record);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return new TransactionCreateResponse(
                savedTransaction.getId(),
                "Transacción creada con éxito"
        );
    }
}