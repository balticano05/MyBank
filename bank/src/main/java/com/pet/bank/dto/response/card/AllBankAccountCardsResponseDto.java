package com.pet.bank.dto.response.card;

import com.pet.bank.dto.response.card.nested.CardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllBankAccountCardsResponseDto {

    private List<CardDto> cards;

}