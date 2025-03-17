package com.pet.bank.service.impl;

import com.pet.bank.dto.mapper.CardMapper;
import com.pet.bank.dto.request.card.CardCreationRequestDto;
import com.pet.bank.dto.response.card.AllBankAccountCardsResponseDto;
import com.pet.bank.dto.response.card.CardCreationResponseDto;
import com.pet.bank.entity.Card;
import com.pet.bank.exception.service.DataValidationService;
import com.pet.bank.exception.type.BadRequestException;
import com.pet.bank.repository.BankAccountRepository;
import com.pet.bank.repository.CardRepository;
import com.pet.bank.service.CardService;
import com.pet.bank.utils.validator.CardNumberRandomGenerator;
import com.pet.bank.utils.validator.FieldValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final BankAccountRepository bankAccountRepository;
    private final DataValidationService dataValidationService;

    @Override
    public AllBankAccountCardsResponseDto findAllBankAccountCards() {
        return CardMapper.mapEntitiesToAllBankAccountCardsResponseDto(cardRepository.findAll());
    }

    @Override
    @Transactional
    public CardCreationResponseDto createCard(UUID bankAccountId, CardCreationRequestDto cardRequest) {

        dataValidationService.existsBankAccountById(bankAccountId, HttpStatus.NOT_FOUND);
        dataValidationService.existsBankAccountById(bankAccountId, HttpStatus.NOT_FOUND);

        if (!FieldValidator.isValidCardType(cardRequest.getCardType())) {
            throw new BadRequestException("Not valid card number");
        }

        Card newCard = Card.builder()
                .cardType(cardRequest.getCardType())
                .cardNumber(CardNumberRandomGenerator.generateCardNumber())
                .bankAccount(bankAccountRepository.findBankAccountById(bankAccountId))
                .isActive(true)
                .build();

        newCard = cardRepository.save(newCard);

        return CardMapper.mapEntityToCardCreationResponseDto(newCard);
    }

    @Override
    public void deleteCardById(UUID cardId) {

        dataValidationService.existsCardById(cardId, HttpStatus.NOT_FOUND);

        cardRepository.deleteById(cardId);
    }

}