package com.pet.bank.dto.response.bank.account.nested;

import com.pet.bank.entity.Currency;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Date;
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

}