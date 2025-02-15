package com.tfg.GoAway.modules.transaction.infrastructure.in.http.create;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.tfg.GoAway.modules.shared.security.SecurityUtils;
import com.tfg.GoAway.modules.transaction.application.create.TransactionCreate;
import com.tfg.GoAway.modules.transaction.application.create.TransactionCreateRecord;
import com.tfg.GoAway.modules.transaction.application.create.TransactionCreateResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionCreatePostController {
 
    private final TransactionCreate transactionCreate;
    private final TransactionCreatePostMapper requestMapper;

    @PostMapping
    public TransactionCreateResponse createTransaction(@RequestBody TransactionCreatePostRequest request) {

        if (request.getStartDate() == null || request.getEndDate() == null) {
            throw new IllegalArgumentException("Las fechas de inicio y fin no pueden ser nulas.");
        }

        if (request.getAdvertisementId() == null || request.getAdvertisementId().isEmpty()) {
            throw new IllegalArgumentException("El ID del anuncio es obligatorio.");
        }

        String tenantEmail = SecurityUtils.getUserEmailFromContext();

        TransactionCreateRecord record = requestMapper.toRecord(request, tenantEmail);
        
        return transactionCreate.execute(record);
    }
}
