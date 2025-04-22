package com.pet.bank.dto.response.user;

import com.pet.bank.dto.response.user.nested.UserFullDto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFullResponseDto {

    private UserFullDto user;

}