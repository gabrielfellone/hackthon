package br.com.techchallenge.hackthon.service;

import java.util.List;
import java.util.Optional;

import br.com.techchallenge.hackthon.entity.enums.TipoSituacaoUsuario;
import br.com.techchallenge.hackthon.exception.ClienteException;
import br.com.techchallenge.hackthon.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.techchallenge.hackthon.dto.UsuarioDTO;
import br.com.techchallenge.hackthon.entity.UsuarioEntity;


@Slf4j
@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<UsuarioDTO> listarTodos(){
		List<UsuarioEntity> usuarios = usuarioRepository.findAll();
		return usuarios.stream().map(UsuarioDTO::new).toList();
	}
	
	public Long inserir(UsuarioDTO usuario) {
		log.info("Criando novo cliente {} ", usuario);

		if (buscaPorLogin(usuario.getLogin()).isPresent()){
			throw new ClienteException("Login já cadastrado, por favor, tente outro!");
		}
		if (buscaPorCpf(usuario.getCpf()).isPresent()){
			throw new ClienteException("CPF já cadastrado, por favor, tente outro!");
		}

		UsuarioEntity usuarioEntity = new UsuarioEntity(usuario);
		usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuarioEntity.setSituacao(TipoSituacaoUsuario.ATIVO);
		try {
            return usuarioRepository.save(usuarioEntity).getId();
		}  catch (ClienteException e) {
			throw new ClienteException("Erro ao salvar cliente!");
		}

	}

	public UsuarioDTO alterar(UsuarioDTO usuario) {
		UsuarioEntity usuarioEntity = new UsuarioEntity(usuario);
		usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
		return new UsuarioDTO(usuarioRepository.save(usuarioEntity));
	}
	
	public void excluir(Long id) {
		UsuarioEntity usuario = usuarioRepository.findById(id).get();
		usuarioRepository.delete(usuario);
	}

	public UsuarioDTO buscarPorId(Long id) {

		return new UsuarioDTO(usuarioRepository.findById(id).get());
	}

	public Optional<UsuarioEntity> buscaPorLogin(String login) {
		return usuarioRepository.findByLogin(login);
	}

	public Optional<UsuarioEntity> buscaPorCpf(String cpf) {
		return usuarioRepository.findByCpf(cpf);
	}
}
