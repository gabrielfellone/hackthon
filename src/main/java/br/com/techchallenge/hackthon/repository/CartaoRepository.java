package br.com.techchallenge.hackthon.repository;

import br.com.techchallenge.hackthon.entity.CartaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartaoRepository extends JpaRepository<CartaoEntity, Long>{
	Optional<CartaoEntity> findByCpf(String cpf);

	Optional<CartaoEntity> findByNumero(String num);
}
