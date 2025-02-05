package com.tfg.GoAway.modules.transaction.application.create;

import com.tfg.GoAway.modules.transaction.domain.Transaction;
import com.tfg.GoAway.modules.transaction.domain.TransactionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionCreate {

    private final TransactionRepository transactionRepository;

    public TransactionCreateResponse execute(TransactionCreateRecord record) {
        
        // Validar que el tenant no sea el mismo que el owner
        if (record.getTenantEmail().equalsIgnoreCase(record.getOwnerEmail())) {
            throw new IllegalArgumentException("No puedes crear una transacción sobre un anuncio que te pertenece.");
        }

        // Convertir el record en un objeto de dominio
        Transaction transaction = TransactionCreateMapper.toDomain(record);

        // Guardar la transacción en la base de datos
        Transaction savedTransaction = transactionRepository.save(transaction);

        return new TransactionCreateResponse(
                savedTransaction.getId(),
                "Transacción creada con éxito"
        );
    }
}
