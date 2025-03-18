package com.pet.bank.security.service;

public interface JwtService {

    String generateToken(String login);

}