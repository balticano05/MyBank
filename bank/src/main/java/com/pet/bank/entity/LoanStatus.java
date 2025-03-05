package com.pet.bank.entity;

import lombok.Getter;

@Getter
public enum LoanStatus {

    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    REPAID("REPAID");

    private final String value;

    LoanStatus(String value) {
        this.value = value;
    }

}
