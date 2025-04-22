package com.pet.bank.dto.response.transaction;

import com.pet.bank.dto.response.transaction.nested.TransactionShortDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllBankAccountTransactionsResponseDto {

    private List<TransactionShortDto> transactions;

}