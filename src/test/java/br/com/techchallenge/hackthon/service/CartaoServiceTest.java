package br.com.techchallenge.hackthon.service;

import br.com.techchallenge.hackthon.dto.CartaoDTO;
import br.com.techchallenge.hackthon.entity.CartaoEntity;
import br.com.techchallenge.hackthon.exception.ClienteException;
import br.com.techchallenge.hackthon.repository.CartaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CartaoServiceTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private CartaoService cartaoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarTodos() {
        CartaoEntity cartao = new CartaoEntity();
        cartao.setCpf("12345678901");
        cartao.setNumero("12321455");
        cartao.setLimite(1000);
        CartaoEntity cartao2 = new CartaoEntity();
        cartao2.setCpf("09876543210");
        cartao2.setNumero("12321455");
        cartao2.setLimite(1000);
        when(cartaoRepository.findAll()).thenReturn(Arrays.asList(cartao, cartao2));

        var result = cartaoService.listarTodos();

        assertEquals(2, result.size());
        assertEquals("12345678901", result.get(0).getCpf());
        assertEquals("09876543210", result.get(1).getCpf());
    }

    @Test
    public void testGerarCartao_Success() {
        CartaoDTO dto = new CartaoDTO();
        dto.setLimite(1000L);
        dto.setId(1L);
        dto.setCpf("12345");
        dto.setNumero("12345");
        dto.setCvv("123");
        dto.setDataValidade("12/25");

        CartaoEntity cartaoEntity = new CartaoEntity(dto);
        when(cartaoRepository.save(any(CartaoEntity.class))).thenReturn(cartaoEntity);
        when(cartaoRepository.findByCpf(dto.getCpf())).thenReturn(Optional.empty());
        when(cartaoRepository.findByNumero(dto.getNumero())).thenReturn(Optional.empty());

        Long id = cartaoService.gerarCartao(dto);

        assertEquals(1L, id);
        verify(cartaoRepository).save(any(CartaoEntity.class));
    }

    @Test
    public void testGerarCartao_ClienteJaCadastrado() {
        CartaoDTO dto = new CartaoDTO();
        dto.setLimite(1000L);
        dto.setId(1L);
        dto.setCpf("12345");
        dto.setNumero("12345");
        dto.setCvv("123");
        dto.setDataValidade("12/25");

        when(cartaoRepository.findByCpf(dto.getCpf())).thenReturn(Optional.of(new CartaoEntity()));
        when(cartaoRepository.findByNumero(dto.getNumero())).thenReturn(Optional.empty());

        ClienteException exception = assertThrows(ClienteException.class, () -> cartaoService.gerarCartao(dto));
        assertEquals("CPF ou Numero já cadastrado, por favor, tente outro!", exception.getMessage());
    }

    @Test
    public void testConsomeLimiteCartao() {
        CartaoEntity cartao = new CartaoEntity();
        cartao.setCpf("123456");
        cartao.setNumero("12321455");
        cartao.setLimite(1000);
        when(cartaoRepository.save(any(CartaoEntity.class))).thenReturn(cartao);

        cartaoService.consomeLimiteCartao((long) 200.0, cartao);

        assertEquals(800.0, cartao.getLimite());
        verify(cartaoRepository).save(cartao);
    }

    @Test
    public void testExcluirPorId() {
        CartaoEntity cartao = new CartaoEntity();
        cartao.setCpf("123456");
        cartao.setNumero("12321455");
        when(cartaoRepository.findById(anyLong())).thenReturn(Optional.of(cartao));

        cartaoService.excluirPorId(1L);

        verify(cartaoRepository).delete(cartao);
    }

    @Test
    public void testBuscarPorId() {
        CartaoEntity cartao = new CartaoEntity();
        cartao.setCpf("12345678901");
        cartao.setNumero("12345678901");
        cartao.setLimite(1000);
        when(cartaoRepository.findById(anyLong())).thenReturn(Optional.of(cartao));

        CartaoDTO dto = cartaoService.buscarPorId(1L);

        assertEquals("12345678901", dto.getCpf());
    }

    @Test
    public void testBuscarCartaoPorCPF() {
        CartaoEntity cartao = new CartaoEntity();
        cartao.setCpf("12345678901");
        cartao.setNumero("12345678901");
        when(cartaoRepository.findByCpf(anyString())).thenReturn(Optional.of(cartao));

        Optional<CartaoEntity> result = cartaoService.buscarCartaoPorCPF("12345678901");

        assertTrue(result.isPresent());
        assertEquals("12345678901", result.get().getCpf());
    }

    @Test
    public void testBuscarCartaoPorNumero() {
        CartaoEntity cartao = new CartaoEntity();
        cartao.setCpf("12345678901");
        cartao.setNumero("1234");
        when(cartaoRepository.findByNumero(anyString())).thenReturn(Optional.of(cartao));

        Optional<CartaoEntity> result = cartaoService.buscarCartaoPorNumero("1234");

        assertTrue(result.isPresent());
        assertEquals("1234", result.get().getNumero());
    }
}