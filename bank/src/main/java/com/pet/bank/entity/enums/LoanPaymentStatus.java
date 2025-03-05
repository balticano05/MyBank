package com.pet.bank.entity;

import lombok.Getter;

@Getter
public enum LoanPaymentStatus {

    COMPLETED("COMPLETED");

    private final String value;

    LoanPaymentStatus(String value) {
        this.value = value;
    }

}
