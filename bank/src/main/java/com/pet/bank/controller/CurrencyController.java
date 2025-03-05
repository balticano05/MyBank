package com.pet.bank.controller;

import com.pet.bank.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/{userId}/loans")
@RequiredArgsConstructor
public class CurrencyController {

    private CurrencyService currencyService;

}
