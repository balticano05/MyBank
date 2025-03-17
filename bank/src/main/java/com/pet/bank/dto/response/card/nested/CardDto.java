package com.pet.bank.dto.response.card.nested;

import com.pet.bank.utils.validator.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

    private UUID id;

    @NotBlank(message = "Card number is required")
    @Pattern(regexp = ValidationConstants.CARD_NUMBER_REGEX,
            message = ValidationConstants.CARD_NUMBER_MESSAGE)
    private String cardNumber;

    private String cardType;

    private boolean isActive;

}