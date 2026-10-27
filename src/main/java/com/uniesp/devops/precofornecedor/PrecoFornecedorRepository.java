package com.uniesp.devops.preco;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrecoFornecedorRepository extends JpaRepository<com.uniesp.devops.preco.PrecoFornecedor, Long> {
}