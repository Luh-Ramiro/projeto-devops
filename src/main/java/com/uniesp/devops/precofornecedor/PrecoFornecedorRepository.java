package com.uniesp.devops.precofornecedor;

import com.uniesp.devops.produto.Produto;
import com.uniesp.devops.fornecedor.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrecoFornecedorRepository extends JpaRepository<PrecoFornecedor, Long> {

    // Método para buscar o último preço registado para o mesmo produto e fornecedor
    Optional<PrecoFornecedor> findTopByProdutoAndFornecedorOrderByDataImportacaoDesc(Produto produto, Fornecedor fornecedor);
}