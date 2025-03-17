package com.pet.bank.entity.enums;

import lombok.Getter;

@Getter
public enum LoanPaymentStatus {

    FAILED("FAILED"),
    COMPLETED("COMPLETED");

    private final String value;

    LoanPaymentStatus(String value) {
        this.value = value;
    }

}