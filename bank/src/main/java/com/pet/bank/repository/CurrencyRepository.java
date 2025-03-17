package com.pet.bank.repository;

import com.pet.bank.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CurrencyRepository extends JpaRepository<Currency, UUID> {

    Currency findCurrencyByCode(String code);

    Boolean existsCurrencyByCode(String code);

}