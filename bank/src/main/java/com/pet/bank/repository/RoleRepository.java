package com.pet.bank.repository;

import com.pet.bank.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Role findRoleByTitle(String title);

}