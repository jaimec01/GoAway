package com.tfg.GoAway.modules.transaction.application.create;

import com.tfg.GoAway.modules.advertisement.domain.Advertisement;
import com.tfg.GoAway.modules.advertisement.domain.AdvertisementRepository;
import com.tfg.GoAway.modules.transaction.domain.Transaction;
import com.tfg.GoAway.modules.transaction.domain.TransactionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionCreate {

    private final TransactionRepository transactionRepository;
    private final AdvertisementRepository advertisementRepository;

    public TransactionCreateResponse execute(TransactionCreateRecord record) {
        
        if (record.getTenantEmail().equalsIgnoreCase(record.getOwnerEmail())) {
            throw new IllegalArgumentException("No puedes crear una transacción sobre un anuncio que te pertenece.");
        }

        Advertisement advertisement = advertisementRepository.findById(record.getAdvertisementId())
                .orElseThrow(() -> new IllegalArgumentException("El anuncio no existe"));

        Transaction transaction = TransactionCreateMapper.toDomain(record, advertisement);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return new TransactionCreateResponse(
                savedTransaction.getId(),
                "Transacción creada con éxito"
        );
    }
}
