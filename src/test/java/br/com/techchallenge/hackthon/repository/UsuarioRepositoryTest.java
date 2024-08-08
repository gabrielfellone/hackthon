package br.com.techchallenge.hackthon.repository;


import br.com.techchallenge.hackthon.entity.UsuarioEntity;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Optional;

import static br.com.techchallenge.hackthon.entity.enums.TipoSituacaoUsuario.ATIVO;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setUp() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setLogin("testUser");
        usuario.setCpf("12345678900");
        usuario.setSenha("12345");
        usuario.setCep("1234");
        usuario.setCidade("batata");
        usuario.setEmail("batata@fiap");
        usuario.setEstado("Batatalandia");
        usuario.setNome("gabriel");
        usuario.setRua("Batata rua");
        usuario.setTelefone("1234-5677");
        usuario.setPais("Brasil");
        usuario.setSituacao(ATIVO);
        usuarioRepository.save(usuario);
    }

    @Test
    public void testFindByLogin() {
        Optional<UsuarioEntity> result = usuarioRepository.findByLogin("testUser");
        assertTrue(result.isPresent(), "Usuário encontrado");
        assertEquals("testUser", result.get().getLogin(), "Login encontrado");
    }

    @Test
    public void testFindByCpf() {
        Optional<UsuarioEntity> result = usuarioRepository.findByCpf("12345678900");
        assertTrue(result.isPresent(), "Usuário nao encontrado por CPF");
        assertEquals("12345678900", result.get().getCpf(), "CPF nao encontrado por CPF");
    }

    @Test
    public void testFindByLogin_NotFound() {
        Optional<UsuarioEntity> result = usuarioRepository.findByLogin("usuarioNaoExistente");
        assertFalse(result.isPresent(), "Usuário Nao devera ser encontrado passando login errado");
    }

    @Test
    public void testFindByCpf_NotFound() {
        Optional<UsuarioEntity> result = usuarioRepository.findByCpf("00000000000");
        assertFalse(result.isPresent(), "Usuário Nao devera ser encontrado passando cpf errado");
    }
}