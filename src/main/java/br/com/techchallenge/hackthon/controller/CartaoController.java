package br.com.techchallenge.hackthon.controller;

import br.com.techchallenge.hackthon.dto.CartaoDTO;
import br.com.techchallenge.hackthon.service.CartaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping(value = "/api/cartao")
@CrossOrigin
public class CartaoController {

	@Autowired
	private CartaoService cartaoService;
	@PostMapping
	public ResponseEntity<Long> inserir(@RequestBody CartaoDTO cartao) {
		log.info("Gerando um novo cartao... {}", cartao);
		return ResponseEntity.status(CREATED).body(cartaoService.gerarCartao(cartao));
	}

}
