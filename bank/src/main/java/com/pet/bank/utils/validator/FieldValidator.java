package com.pet.bank.utils.validator;

import com.pet.bank.entity.BankAccountStatus;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.Collection;

@UtilityClass
public class FieldValidator {

    public boolean isNotEmpty(String value){
        return value != null && !value.trim().isEmpty();
    }

    public boolean isNotNegative(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) >= 0;
    }

    public boolean isNotNull(Object value) {
        return value != null;
    }

    public boolean isNotEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    public boolean isValidCardType(String cardNumber){
        return isNotEmpty(cardNumber);
    }

    public boolean isValidBankAccountStatus(String status) {
        if (status == null) {
            return false;
        }
        for (BankAccountStatus accountStatus : BankAccountStatus.values()) {
            if (accountStatus.getValue().equals(status)) {
                return true;
            }
        }
        return false;
    }

}
