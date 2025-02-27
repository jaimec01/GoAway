package com.tfg.GoAway.modules.transaction.infrastructure.in.http.create;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createTransaction(@RequestBody TransactionCreatePostRequest request) {
        try {
            // Validaciones básicas
            if (request.getStartDate() == null || request.getEndDate() == null) {
                throw new IllegalArgumentException("Las fechas de inicio y fin no pueden ser nulas.");
            }

            // Validación de fechas
            if (request.getEndDate().isBefore(request.getStartDate())) {
                throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
            }

            String tenantEmail = SecurityUtils.getUserEmailFromContext();
            TransactionCreateRecord record = requestMapper.toRecord(request, tenantEmail);

            TransactionCreateResponse response = transactionCreate.execute(record);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error inesperado al crear la transacción.");
        }
    }
}