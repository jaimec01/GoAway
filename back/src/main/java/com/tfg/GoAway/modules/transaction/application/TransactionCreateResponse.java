package com.tfg.GoAway.modules.transaction.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransactionCreateResponse {

    private final String id;
    
    private final String message;
}
