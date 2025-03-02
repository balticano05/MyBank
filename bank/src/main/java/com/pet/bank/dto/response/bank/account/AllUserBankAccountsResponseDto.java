package com.pet.bank.dto.response.bank.account;

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
public class AllUserBankAccountsResponseDto {

    private List<BankAccountDto> bankAccounts;

}