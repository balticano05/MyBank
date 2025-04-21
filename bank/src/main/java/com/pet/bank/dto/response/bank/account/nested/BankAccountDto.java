package com.pet.bank.dto.response.bank.account.nested;

import com.pet.bank.dto.response.card.nested.CardDto;
import com.pet.bank.entity.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto extends RepresentationModel<BankAccountDto> {

    private UUID id;

    private Date createdAt;

    private BigDecimal balance;

    private String status;

    private Currency currency;

    private List<CardDto> cards;

}