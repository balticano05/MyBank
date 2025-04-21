package com.pet.bank.security;

import com.pet.bank.entity.Credential;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    @Getter
    private final UUID userId;
    private final String login;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(Credential credential) {
        userId = credential.getUser().getId();
        login = credential.getLogin();
        password = credential.getPassword();
        authorities = credential.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}