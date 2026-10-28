package com.uniesp.devops.precofornecedor;

import com.uniesp.devops.precofornecedor.PrecoFornecedor;
import com.uniesp.devops.fornecedor.Fornecedor;
import com.uniesp.devops.produto.Produto;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Entity
@Table(name = "preco_fornecedor")
public class PrecoFornecedor {

    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @ManyToOne
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    private Double precoUnitario;

    // Construtores, Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

}
