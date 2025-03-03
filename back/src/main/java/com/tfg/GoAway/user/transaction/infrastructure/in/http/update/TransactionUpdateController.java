package com.tfg.GoAway.user.transaction.infrastructure.in.http.update;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tfg.GoAway.user.shared.security.SecurityUtils;
import com.tfg.GoAway.user.transaction.application.update.TransactionUpdate;
import com.tfg.GoAway.user.transaction.application.update.TransactionUpdateResponse;

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
