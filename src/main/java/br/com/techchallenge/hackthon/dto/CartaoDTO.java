package br.com.techchallenge.hackthon.dto;

import br.com.techchallenge.hackthon.entity.CartaoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartaoDTO {

	private Long id;
	private String cpf;
	private Long limite;
	private String numero;
	private String cvv;
	private String dataValidade;

	public CartaoDTO(CartaoEntity cartao) {
		BeanUtils.copyProperties(cartao, this);
	}


}
