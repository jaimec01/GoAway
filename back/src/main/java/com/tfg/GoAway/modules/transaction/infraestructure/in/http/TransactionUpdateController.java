package com.tfg.GoAway.modules.transaction.infraestructure.in.http;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tfg.GoAway.modules.transaction.application.update.TransactionUpdate;
import com.tfg.GoAway.modules.transaction.application.update.TransactionUpdateResponse;
import com.tfg.GoAway.modules.shared.security.SecurityUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionUpdateController {

    private final TransactionUpdate transactionUpdate;

    @PostMapping("/{transactionId}/accept")
    public ResponseEntity<TransactionUpdateResponse> acceptTransaction(@PathVariable String transactionId) {
        String ownerEmail = SecurityUtils.getUserEmailFromContext();
        return transactionUpdate.acceptTransaction(transactionId, ownerEmail)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{transactionId}/reject")
    public ResponseEntity<TransactionUpdateResponse> rejectTransaction(@PathVariable String transactionId) {
        String ownerEmail = SecurityUtils.getUserEmailFromContext();
        return transactionUpdate.rejectTransaction(transactionId, ownerEmail)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
