package br.com.techchallenge.hackthon.repository;

import br.com.techchallenge.hackthon.entity.PagamentoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PagamentoRepositoryTest {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @BeforeEach
    public void setUp() {
        PagamentoEntity pagamento = new PagamentoEntity();
        pagamento.setCpf("12345678900");
        pagamento.setValor(1000L);
        pagamento.setId(1L);
        pagamento.setCvv("12/24");
        pagamento.setNumero("12345678900");
        pagamentoRepository.save(pagamento);
    }

    @Test
    public void testFindByCpf() {
        Optional<PagamentoEntity> result = pagamentoRepository.findByCpf("12345678900");
        assertTrue(result.isPresent(), "Pagamento encontrado por CPF");
        assertEquals("12345678900", result.get().getCpf(), "CPF encontrado");
    }

    @Test
    public void testFindByCpf_NotFound() {

        Optional<PagamentoEntity> result = pagamentoRepository.findByCpf("00000000000");
        assertFalse(result.isPresent(), "Pagamento nao encontrado por CPF");
    }
}
