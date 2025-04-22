package com.pet.bank.security.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CredentialAuthRequestDto {

    private String login;

    private String password;

}