package com.pet.bank.dto.response.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllBankAccountTransactionsResponseDto {

    private List<TransactionShortDto> transactions;

}
