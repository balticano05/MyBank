package com.pet.bank.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "exchange_rates")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {

    @Id
    @Column(name = "id")
    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_currency_id")
    private Currency baseCurrency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_currency_id")
    private Currency targetCurrency;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "actual_date")
    private LocalDateTime actualDate;

}