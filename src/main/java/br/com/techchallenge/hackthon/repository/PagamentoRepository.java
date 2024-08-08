package br.com.techchallenge.hackthon.repository;

import br.com.techchallenge.hackthon.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<PagamentoEntity, Long>{
	Optional<PagamentoEntity> findByCpf(String cpf);

}
