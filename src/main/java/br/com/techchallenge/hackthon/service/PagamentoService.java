package br.com.techchallenge.hackthon.service;

import br.com.techchallenge.hackthon.dto.PagamentoDTO;
import br.com.techchallenge.hackthon.dto.PagamentoPorClienteDTO;
import br.com.techchallenge.hackthon.entity.CartaoEntity;
import br.com.techchallenge.hackthon.entity.PagamentoEntity;
import br.com.techchallenge.hackthon.exception.PagamentoException;
import br.com.techchallenge.hackthon.repository.PagamentoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class PagamentoService {

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private CartaoService cartaoService;

	final String CHAVE = "Chave do Cartão:";

	public List<PagamentoDTO> listarTodos(){
		List<PagamentoEntity> pagamentos = pagamentoRepository.findAll();
		return pagamentos.stream().map(PagamentoDTO::new).toList();
	}
	public String registraPagamento(PagamentoDTO pagamento){
		log.info("Validando o pagamento {} ", pagamento);

		Optional<CartaoEntity> cartaoOpt = cartaoService.buscarCartaoPorNumero(pagamento.getNumero());

		if(cartaoOpt.isPresent()){

			CartaoEntity cartao = cartaoOpt.get();

			if (validaPagamento(pagamento, cartao)){
				try {
					PagamentoEntity pagamentoEntity = new PagamentoEntity(pagamento);
					cartaoService.consomeLimiteCartao(pagamento.getValor(), cartao);
					pagamentoRepository.save(pagamentoEntity).getId();
				} catch (PagamentoException e) {
					throw new PagamentoException("Erro ao registrar pagamento!");
				}
				return CHAVE.concat(cartao.getId().toString());
			} else throw new PagamentoException("Cartao invalido ou sem limite disponivel!");
		} else throw new PagamentoException("Cartao nao encontrado!");

	}

	public Boolean validaPagamento(PagamentoDTO pagamento, CartaoEntity cartao) {
		log.info("Validando cartão do pagamento ...");

		if (!pagamento.getCvv().equalsIgnoreCase(cartao.getCvv())) {
			log.warn("CVV do pagamento não corresponde ao CVV do cartão.");
			return false;
		}

		if (pagamento.getValor() > cartao.getLimite()) {
			log.warn("Valor do pagamento excede o limite do cartão.");
			return false;
		}

		if (!pagamento.getCpf().equalsIgnoreCase(cartao.getCpf())) {
			log.warn("CPF do pagamento não corresponde ao CPF do cartão.");
			return false;
		}

		if(!validaDataCartao(cartao.getDataValidade())){
			log.warn("Data de validade do cartao vencida.");
			return false;
		}

		return true;
	}

	public static boolean validaDataCartao(String dataCartao) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
		try {
			YearMonth dataInformada = YearMonth.parse(dataCartao, formatter);
			YearMonth agora = YearMonth.now();
			return dataInformada.isAfter(agora);
		} catch (DateTimeParseException e) {
			System.err.println("Formato de data inválido: " + e.getMessage());
			return false;
		}
	}

	public List<PagamentoPorClienteDTO> consultaPagamentoPorCpfCliente(String cpf){
		log.info("Buscando pagamentos do cliente do CPF: {} ", cpf);

		List<PagamentoPorClienteDTO> pagamentoPorClienteDTOS = new ArrayList<>();
		List<PagamentoDTO> pagamentos =
				listarTodos()
						.stream()
						.filter(pagamentoDTO -> pagamentoDTO.getCpf().equalsIgnoreCase(cpf) ).toList();
		if(!pagamentos.isEmpty()){
			pagamentos.forEach(pagamento -> pagamentoPorClienteDTOS.add(new PagamentoPorClienteDTO(pagamento)));
			return pagamentoPorClienteDTOS;
		} else throw new PagamentoException("Pagamentos nao encotrato por cpf do cliente!");
	}





}
