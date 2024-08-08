package br.com.techchallenge.hackthon.service;
import br.com.techchallenge.hackthon.dto.PagamentoDTO;
import br.com.techchallenge.hackthon.dto.PagamentoPorClienteDTO;
import br.com.techchallenge.hackthon.entity.CartaoEntity;
import br.com.techchallenge.hackthon.entity.PagamentoEntity;
import br.com.techchallenge.hackthon.exception.PagamentoException;
import br.com.techchallenge.hackthon.repository.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
public class PagamentoServiceTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private CartaoService cartaoService;

    @InjectMocks
    private PagamentoService pagamentoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarTodos() {
        PagamentoEntity pagamento = new PagamentoEntity();


        pagamento.setCpf("1234567");
        pagamento.setCvv("123");
        pagamento.setNumero("1234567");
        pagamento.setValor(200L);


        PagamentoEntity pagamento2 = new PagamentoEntity();


        pagamento.setCpf("12345671");
        pagamento.setCvv("123");
        pagamento.setNumero("1234567");
        pagamento2.setValor(200L);


        when(pagamentoRepository.findAll()).thenReturn(Arrays.asList(pagamento, pagamento2));

        List<PagamentoDTO> result = pagamentoService.listarTodos();

        assertEquals(2, result.size());
        assertEquals("12345671", result.get(0).getCpf());
    }

    @Test
    public void testRegistraPagamento_Success() {


        PagamentoDTO pagamentoDTO = new PagamentoDTO(1L, "1234", 5000L, "123", "12/25");

        PagamentoEntity pagamento = new PagamentoEntity();

        pagamento.setCpf("1234");
        pagamento.setCvv("12/25");
        pagamento.setId(1L);
        pagamento.setNumero("1234567");
        pagamento.setValor(2000);


        CartaoEntity cartao = new CartaoEntity();
        cartao.setId(1L);
        cartao.setCpf("1234");
        cartao.setNumero("123");
        cartao.setLimite(10000L);
        cartao.setDataValidade("12/25");
        cartao.setCvv("12/25");

        when(cartaoService.buscarCartaoPorNumero(anyString())).thenReturn(Optional.of(cartao));
        when(pagamentoRepository.save(any(PagamentoEntity.class))).thenReturn(pagamento);

        String result = pagamentoService.registraPagamento(pagamentoDTO);

        assertTrue(result.startsWith("Chave do Cartão:"));
        verify(pagamentoRepository).save(any(PagamentoEntity.class));
    }

    @Test
    public void testRegistraPagamento_CartaoNaoEncontrado() {
        PagamentoDTO pagamentoDTO = new PagamentoDTO(1L, "1234", 5000L, "123", "12/25");
        when(cartaoService.buscarCartaoPorNumero(anyString())).thenReturn(Optional.empty());

        PagamentoException exception = assertThrows(PagamentoException.class, () -> pagamentoService.registraPagamento(pagamentoDTO));
        assertEquals("Cartao nao encontrado!", exception.getMessage());
    }

    @Test
    public void testRegistraPagamento_CartaoInvalido() {
        PagamentoDTO pagamentoDTO = new PagamentoDTO(1L, "1234", 5000L, "123", "12/25");
        CartaoEntity cartao = new CartaoEntity();
        cartao.setId(1L);
        cartao.setCpf("1234");
        cartao.setNumero("123");
        cartao.setLimite(1000L);
        cartao.setDataValidade("12/25");
        when(cartaoService.buscarCartaoPorNumero(anyString())).thenReturn(Optional.of(cartao));

        PagamentoException exception = assertThrows(PagamentoException.class, () -> pagamentoService.registraPagamento(pagamentoDTO));
        assertEquals("Cartao invalido ou sem limite disponivel!", exception.getMessage());
    }

    @Test
    public void testValidaPagamento_Success() {
        PagamentoDTO pagamentoDTO = new PagamentoDTO(1L, "1234", 500L, "123", "12/25");
        CartaoEntity cartao = new CartaoEntity();
        cartao.setId(1L);
        cartao.setCpf("1234");
        cartao.setNumero("123");
        cartao.setLimite(1000L);
        cartao.setDataValidade("12/25");
        cartao.setCvv("12/25");
        boolean result = pagamentoService.validaPagamento(pagamentoDTO, cartao);

        assertTrue(result);
    }

    @Test
    public void testValidaPagamento_CvvInvalido() {
        PagamentoDTO pagamentoDTO = new PagamentoDTO(1L, "1234", 5000L, "123", "12/25");
        CartaoEntity cartao = new CartaoEntity();
        cartao.setId(1L);
        cartao.setCpf("1234");
        cartao.setNumero("123");
        cartao.setLimite(1000L);
        cartao.setDataValidade("12/25");

        boolean result = pagamentoService.validaPagamento(pagamentoDTO, cartao);

        assertFalse(result);
    }

    @Test
    public void testValidaDataCartao_Valida() {
        String dataCartao = "12/25";

        boolean result = PagamentoService.validaDataCartao(dataCartao);

        assertTrue(result);
    }

    @Test
    public void testValidaDataCartao_Invalida() {
        String dataCartao = "01/22";

        boolean result = PagamentoService.validaDataCartao(dataCartao);

        assertFalse(result);
    }

    @Test
    public void testConsultaPagamentoPorCpfCliente_Empty() {
        when(pagamentoService.listarTodos()).thenReturn(new ArrayList<>());

        PagamentoException exception = assertThrows(PagamentoException.class, () -> pagamentoService.consultaPagamentoPorCpfCliente("12345678901"));
        assertEquals("Pagamentos nao encotrato por cpf do cliente!", exception.getMessage());
    }
}
