package com.tfg.GoAway.user.transaction.application.create;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.tfg.GoAway.user.advertisement.domain.Advertisement;
import com.tfg.GoAway.user.advertisement.domain.AdvertisementRepository;
import com.tfg.GoAway.user.transaction.domain.Transaction;
import com.tfg.GoAway.user.transaction.domain.TransactionRepository;

@Service
@RequiredArgsConstructor
public class TransactionCreate {

    private final TransactionRepository transactionRepository;
    private final AdvertisementRepository advertisementRepository;

    public TransactionCreateResponse execute(TransactionCreateRecord record) {
        // Validar que el anuncio existe
        Advertisement advertisement = advertisementRepository.findById(record.getAdvertisementId())
                .orElseThrow(() -> new IllegalArgumentException("El anuncio no existe"));

        // Validar que el inquilino no es el propietario del anuncio
        String ownerEmail = advertisement.getUserEmail();
        if (record.getTenantEmail().equalsIgnoreCase(ownerEmail)) {
            throw new IllegalArgumentException("No puedes crear una transacción sobre un anuncio que te pertenece.");
        }

        // Crear y guardar la transacción
        Transaction transaction = TransactionCreateMapper.toDomain(record, advertisement);
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Retornar la respuesta
        return new TransactionCreateResponse(
                savedTransaction.getId(),
                "Transacción creada con éxito"
        );
    }
}