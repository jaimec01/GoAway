package com.tfg.GoAway.user.transaction.domain;

public enum TransactionStatus {
    PENDING("Pending"),
    IN_PROCESS("InProcess"),
    COMPLETED("Completed"),
    CANCELED("Canceled");

    private final String value;

    TransactionStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TransactionStatus fromValue(String value) {
        for (TransactionStatus status : TransactionStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid TransactionStatus value: " + value);
    }

}
