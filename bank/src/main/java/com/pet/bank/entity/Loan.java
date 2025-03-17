package com.pet.bank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "loans")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    @Id
    @Column(name = "id")
    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "status")
    private String status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "loan")
    private List<LoanPayment> loanPayments;

    public void addLoanPayment(LoanPayment payment) {

        if (this.loanPayments == null) {
            this.loanPayments = new ArrayList<>();
        }

        this.loanPayments.add(payment);
    }

}