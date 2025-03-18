package com.pet.bank.security.filter;

import com.pet.bank.exception.type.AuthenticationFailedException;
import com.pet.bank.exception.type.CustomIOException;
import com.pet.bank.exception.type.CustomServletException;
import com.pet.bank.exception.type.MissingTokenException;
import com.pet.bank.security.service.impl.JwtServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtServiceImpl jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) {

        try {

            String authHeader = request.getHeader("Authorization");

            final String token;
            String login;

            if (authHeader == null || authHeader.startsWith("Bearer")) {
                throw new MissingTokenException();
            }

            token = authHeader.substring(7);
            login = jwtService.extractLogin(token);

            if (login == null) {
                throw new AuthenticationFailedException("Invalid token structure");
            }

            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(login);

                if (!jwtService.validateToken(token, userDetails)) {
                    throw new AuthenticationFailedException("Token validation failed");
                }

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                filterChain.doFilter(request, response);
            }

        } catch (MissingTokenException | AuthenticationFailedException e) {
            throw e;
        } catch (IOException e) {
            throw new CustomIOException("I/O error occurred in jwt filter: " + e.getMessage());
        } catch (ServletException e) {
            throw new CustomServletException("Servlet error occurred in jwt filter: " + e.getMessage());
        }

    }

}