package com.tfg.GoAway.user.transaction.domain;

public enum TransactionOwnerConfirmation {
    PENDING("Pending"),
    ACCEPTED("Accepted"),
    REJECTED("Rejected");

    private final String value;

    TransactionOwnerConfirmation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TransactionOwnerConfirmation fromValue(String value) {
        for (TransactionOwnerConfirmation confirmation : TransactionOwnerConfirmation.values()) {
            if (confirmation.value.equalsIgnoreCase(value)) {
                return confirmation;
            }
        }
        throw new IllegalArgumentException("Invalid OwnerConfirmation value: " + value);
    }

}
