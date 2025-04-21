package com.pet.bank.dto.response.bank.account;

import com.pet.bank.dto.response.bank.account.nested.BankAccountDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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