package br.com.techchallenge.hackthon.service;

import br.com.techchallenge.hackthon.entity.UsuarioEntity;
import br.com.techchallenge.hackthon.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserDetailServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UserDetailServiceImpl userDetailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testLoadUserByUsername_UserFound() {

        String username = "testUser";
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setLogin(username);
        when(usuarioRepository.findByLogin(anyString())).thenReturn(Optional.of(usuario));

        UserDetails userDetails = userDetailService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        verify(usuarioRepository).findByLogin(username);
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String username = "testUser";
        when(usuarioRepository.findByLogin(anyString())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userDetailService.loadUserByUsername(username));
        verify(usuarioRepository).findByLogin(username);
    }
}