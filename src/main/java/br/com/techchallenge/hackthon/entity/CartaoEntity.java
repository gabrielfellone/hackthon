package br.com.techchallenge.hackthon.entity;

import br.com.techchallenge.hackthon.dto.CartaoDTO;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

@Entity
@Table(name = "NPL_CARTAO")
public class CartaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String cpf;
    @Column(nullable = false)
    private Long limite;
    @Column(nullable = false, unique = true)
    private String numero;
    @Column(nullable = false)
    private String cvv;
    @Column(nullable = false)
    private String dataValidade;

    public CartaoEntity(CartaoDTO cartao) {
        BeanUtils.copyProperties(cartao, this);
    }

    public CartaoEntity() {

    }

    public Long getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public long getLimite() {
        return limite;
    }

    public void setLimite(long limite) {
        this.limite = limite;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public String getCvv() {
        return cvv;
    }

    public String getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(String dataValidade) {
        this.dataValidade = dataValidade;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CartaoEntity other = (CartaoEntity) obj;
        return Objects.equals(id, other.id);
    }
}
