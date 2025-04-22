package com.pet.bank.service.impl;

import com.pet.bank.controller.LoanController;
import com.pet.bank.controller.UserController;
import com.pet.bank.dto.response.bank.account.AllUserBankAccountsResponseDto;
import com.pet.bank.dto.response.bank.account.nested.BankAccountDto;
import com.pet.bank.dto.response.loan.AllUserLoansResponseDto;
import com.pet.bank.dto.response.loan.nested.LoanShortDto;
import com.pet.bank.dto.response.user.AllUsersShortResponseDto;
import com.pet.bank.dto.response.user.nested.UserShortDto;
import com.pet.bank.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
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

    @Override
    public AllUserLoansResponseDto addLinksToAllUserLoansResponseDto(
            AllUserLoansResponseDto responseDto, UUID userId) {

        List<LoanShortDto> loansWithLinks = responseDto.getLoans().stream()
                .map(loan -> addLinksToLoanShortDto(loan, userId))
                .collect(Collectors.toList());

        responseDto.setLoans(loansWithLinks);
        return addCollectionLinks(responseDto, userId);
    }

    private UserShortDto addLinksToUserShortDto(UserShortDto userShortDto) {
        return userShortDto.add(

                linkTo(UserController.class)
                        .slash(userShortDto.getId())
                        .withRel("delete")
                        .withType("DELETE"),

                linkTo(UserController.class)
                        .slash(userShortDto.getId())
                        .withRel("update")
                        .withType("PUT"),

                linkTo(UserController.class)
                        .slash(userShortDto.getId())
                        .slash("accounts")
                        .withRel("accounts")
                        .withType("GET"),

                linkTo(UserController.class)
                        .slash(userShortDto.getId())
                        .slash("loans")
                        .withRel("loans")
                        .withType("GET")
        );
    }

    private BankAccountDto addLinksToBankAccountDto(BankAccountDto bankAccountDto, UUID userId) {
        return bankAccountDto.add(

                linkTo(UserController.class)
                        .slash(userId)
                        .slash("accounts")
                        .slash(bankAccountDto.getId())
                        .withSelfRel()
                        .withType("GET"),


                linkTo(UserController.class)
                        .slash(userId)
                        .slash("accounts")
                        .slash(bankAccountDto.getId())
                        .withRel("update-account")
                        .withType("PUT"),

                linkTo(UserController.class)
                        .slash(userId)
                        .slash("accounts")
                        .slash(bankAccountDto.getId())
                        .withRel("delete-account")
                        .withType("DELETE"),

                linkTo(methodOn(LoanController.class)
                        .createLoanForUser(userId, bankAccountDto.getId(), null))
                        .withRel("create-loan")
                        .withType("POST"),

                linkTo(methodOn(LoanController.class)
                        .findAllLoansByUserId(userId))
                        .withRel("find-loans")
                        .withType("GET")
        );
    }

    private LoanShortDto addLinksToLoanShortDto(LoanShortDto loanShortDto, UUID userId) {
        return loanShortDto.add(

                linkTo(LoanController.class)
                        .slash(userId)
                        .slash("loans")
                        .withSelfRel()
                        .withType("GET"),

                linkTo(LoanController.class)
                        .slash(userId)
                        .slash("loans")
                        .slash(loanShortDto.getId())
                        .withRel("delete")
                        .withType("DELETE"),

                linkTo(LoanController.class)
                        .slash(userId)
                        .slash("loans")
                        .slash(loanShortDto.getId())
                        .slash("repayments")
                        .withRel("repay")
                        .withType("POST")
        );
    }


    private AllUsersShortResponseDto addCollectionLinks(AllUsersShortResponseDto response) {
        return response.add(

                linkTo(UserController.class)
                        .withRel("create-user")
                        .withType("POST"),

                linkTo(UserController.class)
                        .withSelfRel()
        );
    }

    private AllUserBankAccountsResponseDto addCollectionLinks(
            AllUserBankAccountsResponseDto responseDto, UUID userId) {
        return responseDto.add(

                linkTo(UserController.class)
                        .slash(userId)
                        .slash("accounts")
                        .withRel("create-account")
                        .withType("POST"),

                linkTo(UserController.class)
                        .slash(userId)
                        .slash("accounts")
                        .withSelfRel()
        );
    }


    private AllUserLoansResponseDto addCollectionLinks(
            AllUserLoansResponseDto responseDto, UUID userId) {

        Link selfLink = linkTo(methodOn(LoanController.class)
                .findAllLoansByUserId(userId))
                .withSelfRel();

        Link createLink = linkTo(methodOn(LoanController.class)
                .createLoanForUser(userId, null, null))
                .withRel("create")
                .withType("POST");

        return responseDto.add(selfLink, createLink);
    }

}