package com.tfg.GoAway.user.transaction.infrastructure.in.http.find_all_tenant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tfg.GoAway.shared.security.SecurityUtils;
import com.tfg.GoAway.user.transaction.application.find_all_tenant.TransactionGetByTenant;
import com.tfg.GoAway.user.transaction.application.find_all_tenant.TransactionGetTenantResponse;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionGetByTenantController {

    private final TransactionGetByTenant transactionGetByTenant;

    @GetMapping("/tenant")
    public ResponseEntity<List<TransactionGetTenantResponse>> getTransactionsByTenant(
            @RequestParam(name = "order", defaultValue = "desc") String order) {
        String tenantEmail = SecurityUtils.getUserEmailFromContext();
        List<TransactionGetTenantResponse> transactions = transactionGetByTenant.execute(tenantEmail, order);
        return ResponseEntity.ok(transactions);
    }
}