package br.com.techchallenge.hackthon.dto;

import br.com.techchallenge.hackthon.entity.PagamentoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagamentoDTO {

	private Long id;
	private String cpf;
	private Long valor;
	private String numero;
	private String cvv;

	public PagamentoDTO(PagamentoEntity pagamento) {
		BeanUtils.copyProperties(pagamento, this);
	}


}
