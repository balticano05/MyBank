package com.pet.bank.dto.response.user.nested;

import com.pet.bank.dto.response.bank.account.nested.BankAccountDto;
import com.pet.bank.dto.response.loan.nested.LoanDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFullDto {

    private UUID id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    private Date dateOfBirth;

    private List<BankAccountDto> bankAccounts;

    private List<LoanDto> loans;

}