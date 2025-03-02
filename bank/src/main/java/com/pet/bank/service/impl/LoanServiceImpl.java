package com.pet.bank.service.impl;

import com.pet.bank.dto.mapper.LoanMapper;
import com.pet.bank.dto.response.loan.AllUserLoansResponseDto;
import com.pet.bank.dto.response.loan.LoanFullResponseDto;
import com.pet.bank.repository.LoanRepository;
import com.pet.bank.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    @Override
    public AllUserLoansResponseDto findAllLoansByUserId(UUID userId) {
        return LoanMapper.mapEntitiesToAllUserLoansResponseDto(loanRepository.findAllLoansByUserId(userId));
    }

    public LoanFullResponseDto findLoanById(UUID loanId){
        return LoanMapper.mapEntityToFullResponseDto(loanRepository.findByLoanId(loanId));
    }

}
