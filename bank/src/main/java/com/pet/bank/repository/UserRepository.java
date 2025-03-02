package com.pet.bank.repository;

import com.pet.bank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findUserById(UUID id);

}