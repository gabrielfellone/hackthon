package br.com.techchallenge.hackthon.entity;

import br.com.techchallenge.hackthon.dto.PagamentoDTO;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

@Entity
@Table(name = "NPL_PAGAMENTO")
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String cpf;
    @Column(nullable = false)
    private Long valor;
    @Column(nullable = false)
    private String numero;
    @Column(nullable = false)
    private String cvv;

    public PagamentoEntity(PagamentoDTO pagamentoDTO) {
        BeanUtils.copyProperties(pagamentoDTO, this);
    }

    public PagamentoEntity() {

    }

    public Long getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public long getValor() {
        return valor;
    }

    public void setValor(long valor) {
        this.valor = valor;
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
        PagamentoEntity other = (PagamentoEntity) obj;
        return Objects.equals(id, other.id);
    }
}
