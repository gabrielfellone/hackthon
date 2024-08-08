package br.com.techchallenge.hackthon.controller;

import br.com.techchallenge.hackthon.dto.PagamentoDTO;
import br.com.techchallenge.hackthon.dto.PagamentoPorClienteDTO;
import br.com.techchallenge.hackthon.service.PagamentoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping(value = "/api/pagamentos")
@CrossOrigin
public class PagamentoController {

	@Autowired
	private PagamentoService pagamentoService;
	@PostMapping
	public ResponseEntity<String> inserir(@RequestBody PagamentoDTO pagamento) {
		log.info("Registrando um novo pagamento... {}", pagamento);
		return ResponseEntity.status(CREATED).body(pagamentoService.registraPagamento(pagamento));
	}

	@GetMapping("/cliente/{cpf}")
	public List<PagamentoPorClienteDTO> consultaPagamentoPorCpfCliente(@PathVariable("cpf") String cpf){
		log.info("Consultando pagamento por CPF do Cliente ...");
		return pagamentoService.consultaPagamentoPorCpfCliente(cpf);
	}

}
