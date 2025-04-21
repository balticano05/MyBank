package com.pet.bank.service;

import com.pet.bank.dto.response.bank.account.AllUserBankAccountsResponseDto;
import com.pet.bank.dto.response.user.AllUsersShortResponseDto;

import java.util.UUID;

public interface LinkService {

    AllUsersShortResponseDto addLinksToAllUsersShortResponseDto(AllUsersShortResponseDto allUsersShortResponseDto);

    AllUserBankAccountsResponseDto addLinksToAllUserBankAccountsResponseDto(AllUserBankAccountsResponseDto allUserBankAccountsResponseDto, UUID userId);

}
