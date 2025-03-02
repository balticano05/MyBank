package com.pet.bank.dto.response.transaction;

import com.pet.bank.dto.response.bank.account.BankAccountIdDto;
import com.pet.bank.dto.response.currency.CurrencyShortDto;
import com.pet.bank.entity.BankAccount;
import com.pet.bank.entity.Currency;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionShortDto {

    private UUID id;

    private UUID fromAccountId;

    private UUID toAccountId;

    private BigDecimal amount;

    private CurrencyShortDto currency;

    private String transactionType;

    private Date createdAt;

}
