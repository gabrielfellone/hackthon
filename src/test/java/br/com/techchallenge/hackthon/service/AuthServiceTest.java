package br.com.techchallenge.hackthon.service;

import br.com.techchallenge.hackthon.dto.AcessDTO;
import br.com.techchallenge.hackthon.dto.AuthenticationDTO;
import br.com.techchallenge.hackthon.security.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticatioManager;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin_Success() {

        AuthenticationDTO authDto = new AuthenticationDTO();
        authDto.setPassword("password");
        authDto.setUsername("username");
        UserDetailsImpl userDetails =
                new UserDetailsImpl(1L, "fiapAuthTeste","username", "password","fiapAuthTeste", new ArrayList<>());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        when(authenticatioManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtils.generateTokenFromUserDetailsImpl(any(UserDetailsImpl.class))).thenReturn("valid_token");

        AcessDTO result = authService.login(authDto);

        // Assert
        assertEquals("valid_token", result.getToken());
        verify(authenticatioManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils).generateTokenFromUserDetailsImpl(any(UserDetailsImpl.class));
    }

    @Test
    public void testLogin_Failure() {
        AuthenticationDTO authDto = new AuthenticationDTO();
        authDto.setPassword("password");
        authDto.setUsername("username");

        when(authenticatioManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        AcessDTO result = authService.login(authDto);

        assertEquals("Acesso negado", result.getToken());
        verify(authenticatioManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}