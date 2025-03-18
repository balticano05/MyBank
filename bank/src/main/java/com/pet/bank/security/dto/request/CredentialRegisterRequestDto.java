package com.pet.bank.security.dto.request;

import com.pet.bank.security.dto.request.nested.RolesIdDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CredentialRegisterRequestDto {

    private String login;

    private String email;

    private String password;

    private RolesIdDto roles;

}