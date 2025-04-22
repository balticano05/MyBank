package com.pet.bank.security.dto.request.nested;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolesIdDto {

    private List<UUID> rolesId;

}