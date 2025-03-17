package com.pet.bank.repository;

import com.pet.bank.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, UUID> {

    ExchangeRate findByBaseCurrency_CodeAndTargetCurrency_Code(String baseCurrency, String targetCurrency);

    boolean existsByBaseCurrency_CodeAndTargetCurrency_Code(String baseCurrency, String targetCurrency);

}