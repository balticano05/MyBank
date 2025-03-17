package com.pet.bank.dto.response.user;

import com.pet.bank.dto.response.user.nested.UserFullDto;

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
public class UserFullResponseDto {

    private UserFullDto user;

}