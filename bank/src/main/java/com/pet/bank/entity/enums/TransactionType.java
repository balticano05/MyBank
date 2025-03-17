package com.pet.bank.entity.enums;

import lombok.Getter;

@Getter
public enum TransactionType {

    TRANSFER("TRANSFER"),
    DEPOSIT("DEPOSIT");

    private final String value;

    TransactionType(String value) {
        this.value = value;
    }

}