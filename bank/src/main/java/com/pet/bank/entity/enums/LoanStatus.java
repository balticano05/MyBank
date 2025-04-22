package com.pet.bank.entity.enums;

import lombok.Getter;

@Getter
public enum LoanStatus {

    ACTIVE("ACTIVE"),
    REPAID("REPAID");

    private final String value;

    LoanStatus(String value) {
        this.value = value;
    }

}