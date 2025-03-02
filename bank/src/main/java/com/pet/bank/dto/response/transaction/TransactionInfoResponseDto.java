package com.pet.bank.dto.response.transaction;

import com.pet.bank.dto.response.currency.CurrencyDto;

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
public class TransactionInfoResponseDto {

    private UUID id;

    private UUID fromAccountId;

    private UUID toAccountId;

    private BigDecimal amount;

    private CurrencyDto currency;

    private String transactionType;

    private Date createdAt;

    private String description;

}
