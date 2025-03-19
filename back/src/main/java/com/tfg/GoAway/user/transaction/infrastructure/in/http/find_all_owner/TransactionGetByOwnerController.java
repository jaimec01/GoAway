package com.tfg.GoAway.user.transaction.infrastructure.in.http.find_all_owner;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tfg.GoAway.shared.security.SecurityUtils;
import com.tfg.GoAway.user.transaction.application.find_all_owner.TransactionGetByOwner;
import com.tfg.GoAway.user.transaction.application.find_all_owner.TransactionGetOwnerResponse;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionGetByOwnerController {

    private final TransactionGetByOwner transactionGetByOwner;

    @GetMapping("/owner")
    public ResponseEntity<List<TransactionGetOwnerResponse>> getTransactionsByOwner(
            @RequestParam(name = "order", defaultValue = "desc") String order) {
        String ownerEmail = SecurityUtils.getUserEmailFromContext();
        List<TransactionGetOwnerResponse> transactions = transactionGetByOwner.execute(ownerEmail, order);
        return ResponseEntity.ok(transactions);
    }
}