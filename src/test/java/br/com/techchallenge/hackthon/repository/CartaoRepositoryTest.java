package br.com.techchallenge.hackthon.repository;

import br.com.techchallenge.hackthon.entity.CartaoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringJUnitConfig
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartaoRepositoryTest {

    @Autowired
    private CartaoRepository cartaoRepository;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testFindByCpf() {
        CartaoEntity cartao = new CartaoEntity();
        cartao.setCpf("12345678900");
        cartao.setNumero("1234567812345678");
        cartao.setCvv("02/29");
        cartao.setDataValidade("02/29");
        cartao.setLimite(1000L);
        cartao.setId(1L);
        cartaoRepository.save(cartao);

        Optional<CartaoEntity> result = cartaoRepository.findByCpf("12345678900");

        assertTrue(result.isPresent());
        assertEquals("12345678900", result.get().getCpf());
    }

    @Test
    public void testFindByNumero() {
        CartaoEntity cartao = new CartaoEntity();
        cartao.setCpf("12345678900");
        cartao.setNumero("1234567812345678");
        cartao.setCvv("02/29");
        cartao.setDataValidade("02/29");
        cartao.setLimite(1000L);
        cartao.setId(1L);
        cartaoRepository.save(cartao);

        Optional<CartaoEntity> result = cartaoRepository.findByNumero("1234567812345678");

        assertTrue(result.isPresent());
        assertEquals("1234567812345678", result.get().getNumero());
    }
}