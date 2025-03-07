package com.pet.bank.service;

import com.pet.bank.dto.request.card.CardCreationRequestDto;
import com.pet.bank.dto.response.card.AllBankAccountCardsResponseDto;
import com.pet.bank.dto.response.card.CardCreationResponseDto;

import java.util.UUID;

public interface CardService {

    AllBankAccountCardsResponseDto findAllBankAccountCards();

    CardCreationResponseDto createCard(CardCreationRequestDto cardRequest);

    void deleteCardById(UUID cardId);

}