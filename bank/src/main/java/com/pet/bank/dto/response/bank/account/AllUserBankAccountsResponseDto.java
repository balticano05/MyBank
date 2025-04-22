package com.pet.bank.dto.response.bank.account;

import com.pet.bank.dto.response.bank.account.nested.BankAccountDto;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllUserBankAccountsResponseDto extends RepresentationModel<AllUserBankAccountsResponseDto> {

    private List<BankAccountDto> bankAccounts;

}