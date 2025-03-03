package com.tfg.GoAway.user.transaction.application.create;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransactionCreateResponse {

    private final String id;
    
    private final String message;
}
