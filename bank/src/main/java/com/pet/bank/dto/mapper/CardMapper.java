package com.pet.bank.dto.mapper;

import com.pet.bank.dto.response.card.AllBankAccountCardsResponseDto;
import com.pet.bank.dto.response.card.CardCreationResponseDto;
import com.pet.bank.dto.response.card.nested.CardDto;
import com.pet.bank.entity.Card;

import java.util.ArrayList;
import java.util.List;

public class CardMapper {

    public static CardDto mapEntityToCardDto(Card source) {
        return CardDto.builder()
                .id(source.getId())
                .cardNumber(source.getCardNumber())
                .cardType(source.getCardType())
                .isActive(source.isActive())
                .build();
    }

    public static CardCreationResponseDto mapEntityToCardCreationResponseDto(Card card) {
        return CardCreationResponseDto.builder()
                .id(card.getId())
                .cardNumber(card.getCardNumber())
                .cardType(card.getCardType())
                .isActive(card.isActive())
                .build();
    }

    public static AllBankAccountCardsResponseDto mapEntitiesToAllBankAccountCardsResponseDto(List<Card> cards) {
        return AllBankAccountCardsResponseDto.builder()
                .cards(mapEntitiesToListCardDto(cards))
                .build();
    }

    public static List<CardDto> mapEntitiesToListCardDto(List<Card> source) {

        if (source == null)
            return new ArrayList<>();

        return source.stream()
                .map(CardMapper::mapEntityToCardDto)
                .toList();

    }

}