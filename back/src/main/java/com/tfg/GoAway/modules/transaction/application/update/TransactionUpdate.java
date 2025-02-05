package com.tfg.GoAway.modules.transaction.application.update;

import com.tfg.GoAway.modules.transaction.domain.Transaction;
import com.tfg.GoAway.modules.transaction.domain.TransactionRepository;
import com.tfg.GoAway.modules.transaction.domain.TransactionOwnerConfirmation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionUpdate {

    private final TransactionRepository transactionRepository;

    public Optional<TransactionUpdateResponse> acceptTransaction(String transactionId, String ownerEmail) {
        return updateTransactionStatus(transactionId, ownerEmail, TransactionOwnerConfirmation.ACCEPTED);
    }

    public Optional<TransactionUpdateResponse> rejectTransaction(String transactionId, String ownerEmail) {
        return updateTransactionStatus(transactionId, ownerEmail, TransactionOwnerConfirmation.REJECTED);
    }

    private Optional<TransactionUpdateResponse> updateTransactionStatus(String transactionId, String ownerEmail, TransactionOwnerConfirmation newStatus) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        
        if (optionalTransaction.isEmpty()) {
            return Optional.empty();
        }

        Transaction transaction = optionalTransaction.get();

        // Verificar que el usuario es el dueño del anuncio
        if (!transaction.getOwnerEmail().equals(ownerEmail)) {
            throw new IllegalArgumentException("No tienes permiso para modificar esta transacción.");
        }

        // Verificar que la transacción no ha sido ya aceptada o rechazada
        if (transaction.getOwnerConfirmation() != TransactionOwnerConfirmation.PENDING) {
            throw new IllegalArgumentException("La transacción ya ha sido procesada.");
        }

        transaction.setOwnerConfirmation(newStatus);
        transactionRepository.save(transaction);

        return Optional.of(new TransactionUpdateResponse(transaction.getId(), "Transacción " + newStatus.name().toLowerCase() + " con éxito"));
    }
}
