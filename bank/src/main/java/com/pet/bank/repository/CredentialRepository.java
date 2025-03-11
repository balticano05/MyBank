package com.pet.bank.repository;

import com.pet.bank.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CredentialRepository extends JpaRepository<Credential, UUID> {

    Credential findCredentialById(UUID credentialId);

}
