package br.com.techchallenge.hackthon.service;

import br.com.techchallenge.hackthon.dto.CartaoDTO;
import br.com.techchallenge.hackthon.entity.CartaoEntity;
import br.com.techchallenge.hackthon.exception.ClienteException;
import br.com.techchallenge.hackthon.repository.CartaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class CartaoService {

	@Autowired
	private CartaoRepository cartaoRepository;

	@Autowired
	private UsuarioService usuarioService;

	public List<CartaoDTO> listarTodos(){
		List<CartaoEntity> cartoes = cartaoRepository.findAll();
		return cartoes.stream().map(CartaoDTO::new).toList();
	}

	public Long gerarCartao(CartaoDTO cartao) {
		log.info("Validando cliente ... {}", cartao.getCpf());
		if (validaCliente(cartao.getCpf(), cartao.getNumero())){
			try {
				CartaoEntity cartaoEntity = new CartaoEntity(cartao);
				return cartaoRepository.save(cartaoEntity).getId();
			}  catch (ClienteException e) {
				throw new ClienteException("Erro ao salvar CARTAO!");
			}
		} else throw new ClienteException("CPF ou Numero já cadastrado, por favor, tente outro!");
	}

	public Boolean validaCliente(String CPF, String num){
		log.info("Validando se ja existe cartao gerado com mesmo CPF...");
		if (buscarCartaoPorCPF(CPF).isPresent()){
			return false;
		}
		log.info("Validando se ja existe numero de cartao de credito igual...");
        return buscarCartaoPorNumero(num).isEmpty();
    }

	public void consomeLimiteCartao(Long valor, CartaoEntity cartao){
		log.info("Consumindo valor {} do pagamento no cartao {} ", valor, cartao.getId());
		cartao.setLimite(cartao.getLimite() - valor);
		salvaCartao(cartao);
	}


	public void salvaCartao(CartaoEntity cartao){
		log.info("Atualizando cartao na base {} ", cartao );
		cartaoRepository.save(cartao);
	}

	public void excluirPorId(Long id) {
		CartaoEntity cartao = cartaoRepository.findById(id).get();
		cartaoRepository.delete(cartao);
	}
	public CartaoDTO buscarPorId(Long id) {
		return new CartaoDTO(cartaoRepository.findById(id).get());
	}

	public Optional<CartaoEntity> buscarCartaoPorCPF(String CPF) {
		return cartaoRepository.findByCpf(CPF);
	}

	public Optional<CartaoEntity> buscarCartaoPorNumero(String num) {
		return cartaoRepository.findByNumero(num);
	}


}
