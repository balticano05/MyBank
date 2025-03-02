package com.pet.bank.dto.response.bank.account;

import com.pet.bank.dto.response.card.CardDto;
import com.pet.bank.dto.response.currency.CurrencyDto;
import com.pet.bank.dto.response.loan.LoanShortDto;
import com.pet.bank.dto.response.user.nested.UserShortDto;
import com.pet.bank.entity.Card;
import com.pet.bank.entity.Currency;
import com.pet.bank.entity.Loan;
import com.pet.bank.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountFullResponseDto {

    private UUID id;

    private UserShortDto owner;

    private Date createdAt;

    private BigDecimal balance;

    private String status;

    private CurrencyDto currency;

    private List<CardDto> cards;

    private List<LoanShortDto> loans;

}
