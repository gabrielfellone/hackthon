package br.com.techchallenge.hackthon.dto;

import br.com.techchallenge.hackthon.entity.enums.TipoSituacaoUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import br.com.techchallenge.hackthon.entity.UsuarioEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {

	private Long id;
	private String cpf;
	private String nome;
	private String email;
	private String rua;
	private String cidade;
	private String estado;
	private String cep;
	private String pais;
	private String telefone;
	private String login;	
	private String senha;
	private TipoSituacaoUsuario situacao;
	
	public UsuarioDTO(UsuarioEntity usuario) {
		BeanUtils.copyProperties(usuario, this);
	}


}
