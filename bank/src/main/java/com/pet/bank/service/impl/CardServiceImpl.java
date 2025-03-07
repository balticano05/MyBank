package com.pet.bank.service.impl;

import com.pet.bank.dto.mapper.CardMapper;
import com.pet.bank.dto.request.card.CardCreationRequestDto;
import com.pet.bank.dto.response.card.AllBankAccountCardsResponseDto;
import com.pet.bank.dto.response.card.CardCreationResponseDto;
import com.pet.bank.entity.Card;
import com.pet.bank.repository.CardRepository;
import com.pet.bank.service.CardService;
import com.pet.bank.utils.validator.CardNumberRandomGenerator;
import com.pet.bank.utils.validator.FieldValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Override
    public AllBankAccountCardsResponseDto findAllBankAccountCards() {
        return CardMapper.mapEntitiesToAllBankAccountCardsResponseDto(cardRepository.findAll());
    }

    @Override
    @Transactional
    public CardCreationResponseDto createCard(CardCreationRequestDto cardRequest) {

        Card newCard = new Card();

        if (FieldValidator.isValidCardType(cardRequest.getCardType())) {
            newCard.setCardType(cardRequest.getCardType());
        }

        newCard.setCardNumber(CardNumberRandomGenerator.generateCardNumber());
        newCard.setActive(true);

        return CardMapper.mapEntityToCardCreationResponseDto(newCard);
    }

    @Override
    public void deleteCardById(UUID cardId) {
        cardRepository.deleteById(cardId);
    }

}