package br.com.techchallenge.hackthon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagamentoPorClienteDTO {


	private String metodo;
	private Long valor;
	private String descricao;
	private String status;


	public PagamentoPorClienteDTO(PagamentoDTO pagamento){
		this.valor = pagamento.getValor();
		this.metodo = "CARTAO DE CREDITO";
		this.descricao = "COMPRA ONLINE";
		this.status = "EFETUADO COM SUCESSO";

	}

}
