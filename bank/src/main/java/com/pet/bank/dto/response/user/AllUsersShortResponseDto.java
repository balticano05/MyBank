package com.pet.bank.dto.response.user;

import com.pet.bank.dto.response.user.nested.UserShortDto;
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
public class AllUsersShortResponseDto {

    private List<UserShortDto> users;

}
