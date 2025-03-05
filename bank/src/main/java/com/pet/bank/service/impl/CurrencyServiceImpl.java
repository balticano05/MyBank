package com.pet.bank.service.impl;

import com.pet.bank.repository.CurrencyRepository;
import com.pet.bank.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private CurrencyRepository currencyRepository;


}
