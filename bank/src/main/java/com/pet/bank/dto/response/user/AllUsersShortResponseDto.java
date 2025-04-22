package com.pet.bank.dto.response.user;

import com.pet.bank.dto.response.user.nested.UserShortDto;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllUsersShortResponseDto extends RepresentationModel<AllUsersShortResponseDto> {

    private List<UserShortDto> users;

}