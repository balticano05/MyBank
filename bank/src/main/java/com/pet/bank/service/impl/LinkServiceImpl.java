package com.pet.bank.service.impl;

import com.pet.bank.controller.BankAccountController;
import com.pet.bank.controller.UserController;
import com.pet.bank.dto.request.user.UserUpdateRequestDto;
import com.pet.bank.dto.response.bank.account.AllUserBankAccountsResponseDto;
import com.pet.bank.dto.response.bank.account.nested.BankAccountDto;
import com.pet.bank.dto.response.user.AllUsersShortResponseDto;
import com.pet.bank.dto.response.user.nested.UserShortDto;
import com.pet.bank.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    @Override
    public AllUsersShortResponseDto addLinksToAllUsersShortResponseDto(AllUsersShortResponseDto allUsersShortResponseDto) {
        List<UserShortDto> usersWithLinks = allUsersShortResponseDto.getUsers().stream()
                .map(this::addLinksToUserShortDto)
                .collect(Collectors.toList());
        allUsersShortResponseDto.setUsers(usersWithLinks);
        return addCollectionLinks(allUsersShortResponseDto);
    }

    @Override
    public AllUserBankAccountsResponseDto addLinksToAllUserBankAccountsResponseDto(
            AllUserBankAccountsResponseDto allUserBankAccountsResponseDto, UUID userId) {
        List<BankAccountDto> accountsWithLinks = allUserBankAccountsResponseDto.getBankAccounts().stream()
                .map(account -> addLinksToBankAccountDto(account, userId))
                .collect(Collectors.toList());
        allUserBankAccountsResponseDto.setBankAccounts(accountsWithLinks);
        return addCollectionLinks(allUserBankAccountsResponseDto, userId);
    }

    private UserShortDto addLinksToUserShortDto(UserShortDto userShortDto) {
        return userShortDto.add(
                // DELETE /api/v1/users/{id}
                linkTo(UserController.class)
                        .slash(userShortDto.getId())
                        .withRel("delete")
                        .withType("DELETE"),

                // PUT /api/v1/users/{id}
                linkTo(UserController.class)
                        .slash(userShortDto.getId())
                        .withRel("update")
                        .withType("PUT"),

                // GET /api/v1/users/{id}/accounts
                linkTo(UserController.class)
                        .slash(userShortDto.getId())
                        .slash("accounts")
                        .withRel("accounts")
                        .withType("GET")
        );
    }

    private AllUsersShortResponseDto addCollectionLinks(AllUsersShortResponseDto response) {
        return response.add(
                // POST /api/v1/users
                linkTo(UserController.class)
                        .withRel("create-user")
                        .withType("POST"),

                // GET /api/v1/users
                linkTo(UserController.class)
                        .withSelfRel()
        );
    }

    private AllUserBankAccountsResponseDto addCollectionLinks(
            AllUserBankAccountsResponseDto responseDto, UUID userId) {
        return responseDto.add(
                // POST /api/v1/users/{userId}/accounts
                linkTo(UserController.class)
                        .slash(userId)
                        .slash("accounts")
                        .withRel("create-account")
                        .withType("POST"),

                // GET /api/v1/users/{userId}/accounts
                linkTo(UserController.class)
                        .slash(userId)
                        .slash("accounts")
                        .withSelfRel()
        );
    }

    private BankAccountDto addLinksToBankAccountDto(BankAccountDto bankAccountDto, UUID userId) {
        return bankAccountDto.add(
                // GET /api/v1/users/{userId}/accounts/{accountId}
                linkTo(UserController.class)
                        .slash(userId)
                        .slash("accounts")
                        .slash(bankAccountDto.getId())
                        .withSelfRel()
                        .withType("GET"),

                // PUT /api/v1/users/{userId}/accounts/{accountId}
                linkTo(UserController.class)
                        .slash(userId)
                        .slash("accounts")
                        .slash(bankAccountDto.getId())
                        .withRel("update-account")
                        .withType("PUT"),

                // DELETE /api/v1/users/{userId}/accounts/{accountId}
                linkTo(UserController.class)
                        .slash(userId)
                        .slash("accounts")
                        .slash(bankAccountDto.getId())
                        .withRel("delete-account")
                        .withType("DELETE"),

                // GET /api/v1/users/{userId}/accounts
                linkTo(UserController.class)
                        .slash(userId)
                        .slash("accounts")
                        .withRel("back-to-accounts")
                        .withType("GET")
        );
    }
}