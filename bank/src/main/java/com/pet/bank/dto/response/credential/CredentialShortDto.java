package com.pet.bank.dto.response.credential;

import com.pet.bank.dto.response.role.RoleShortDto;
import com.pet.bank.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CredentialShortDto {

    private String login;

    private String email;

    private String password;

    private List<RoleShortDto> roles;

}
