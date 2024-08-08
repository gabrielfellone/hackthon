package br.com.techchallenge.hackthon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.techchallenge.hackthon.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>{

	Optional<UsuarioEntity> findByLogin(String login);

	Optional<UsuarioEntity> findByCpf(String cpf);
}
