package com.pet.bank.dto.response.transaction.nested;

import com.pet.bank.dto.response.currency.CurrencyShortDto;
import lombok.*;

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