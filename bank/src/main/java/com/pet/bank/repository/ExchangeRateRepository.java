package com.pet.bank.repository;

import com.pet.bank.entity.Currency;
import com.pet.bank.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, UUID> {
    ExchangeRate findByBaseCurrencyAndTargetCurrency(Currency fromCurrency, Currency toCurrency);

    boolean existsByBaseCurrencyIdAndTargetCurrencyId(Currency fromCurrency, Currency toCurrency);

}
