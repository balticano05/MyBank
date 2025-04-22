package com.pet.bank.security.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CredentialAuthResponseDto {

    private String token;

}