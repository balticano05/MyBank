package com.pet.bank.entity.enums;

import lombok.Getter;

@Getter
public enum BankAccountStatus {

    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    CLOSED("CLOSED");

    private final String value;

    BankAccountStatus(String value) {
        this.value = value;
    }

}