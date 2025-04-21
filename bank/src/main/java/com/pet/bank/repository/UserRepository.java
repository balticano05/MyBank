package com.pet.bank.repository;

import com.pet.bank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    User findUserById(UUID id);

    boolean existsUserByCredentialId(UUID credentialId);

}