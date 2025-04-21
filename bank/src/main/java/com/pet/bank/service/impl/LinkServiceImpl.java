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
                linkTo(UserController.class)
                        .slash(userShortDto.getId())
                        .withRel("delete")
                        .withType("DELETE"),

                linkTo(methodOn(UserController.class)
                        .createUpdate(userShortDto.getId(), UserUpdateRequestDto.builder().build()))
                        .withRel("update")
                        .withType("PUT"),

                linkTo(methodOn(BankAccountController.class)
                        .findAllBankAccounts(userShortDto.getId()))
                        .withRel("accounts")
                        .withType("GET")

        );

    }

    private AllUsersShortResponseDto addCollectionLinks(AllUsersShortResponseDto response) {
        response.add(
                linkTo(methodOn(UserController.class).createUser(null))
                        .withRel("create-user")
                        .withType("POST"),

                linkTo(methodOn(UserController.class).findAllUsersByParameters(null))
                        .withSelfRel()
        );

        return response;
    }

    private AllUserBankAccountsResponseDto addCollectionLinks(
            AllUserBankAccountsResponseDto responseDto, UUID userId) {
        return responseDto.add(

                linkTo(methodOn(BankAccountController.class)
                        .createBankAccountForUser(userId, null))
                        .withRel("create-account")
                        .withType("POST"),

                linkTo(methodOn(BankAccountController.class)
                        .findAllBankAccounts(userId))
                        .withSelfRel()
        );
    }

    private BankAccountDto addLinksToBankAccountDto(BankAccountDto bankAccountDto, UUID userId) {
        return bankAccountDto.add(

                linkTo(methodOn(BankAccountController.class)
                        .findBankAccountById(bankAccountDto.getId()))
                        .withSelfRel()
                        .withType("GET"),

                linkTo(methodOn(BankAccountController.class)
                        .updateBankAccountById(bankAccountDto.getId(), null))
                        .withRel("update-account")
                        .withType("PUT"),

                linkTo(methodOn(BankAccountController.class)
                        .deleteBankAccountById(bankAccountDto.getId()))
                        .withRel("delete-account")
                        .withType("DELETE"),

                linkTo(methodOn(BankAccountController.class)
                        .findAllBankAccounts(userId))
                        .withRel("back-to-accounts")
                        .withType("GET")
        );
    }

}
