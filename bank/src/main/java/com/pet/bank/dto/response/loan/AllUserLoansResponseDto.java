package com.pet.bank.dto.response.loan;

import com.pet.bank.dto.response.loan.nested.LoanShortDto;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllUserLoansResponseDto extends RepresentationModel<AllUserLoansResponseDto> {

    private List<LoanShortDto> loans;

}