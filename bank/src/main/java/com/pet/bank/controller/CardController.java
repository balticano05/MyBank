package com.pet.bank.controller;

import com.pet.bank.dto.request.card.CardCreationRequestDto;
import com.pet.bank.dto.response.card.AllBankAccountCardsResponseDto;
import com.pet.bank.dto.response.card.CardCreationResponseDto;
import com.pet.bank.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/{bankAccountId}/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping
    public AllBankAccountCardsResponseDto findAllBankAccountCards() {
        return cardService.findAllBankAccountCards();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardCreationResponseDto createCard(
            @PathVariable UUID bankAccountId,
            @RequestBody CardCreationRequestDto cardRequest
    ) {
        return cardService.createCard(bankAccountId, cardRequest);
    }

    @DeleteMapping("/{cardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCardById(@PathVariable UUID cardId) {
        cardService.deleteCardById(cardId);
    }

}