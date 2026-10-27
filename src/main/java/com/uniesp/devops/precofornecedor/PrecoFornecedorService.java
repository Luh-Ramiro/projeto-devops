package com.uniesp.devops.precofornecedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrecoFornecedorService {

    @Autowired
    private com.uniesp.devops.preco.PrecoFornecedorRepository repository;

    public List<com.uniesp.devops.preco.PrecoFornecedor> listarTodos() {
        return repository.findAll();
    }

    public com.uniesp.devops.preco.PrecoFornecedor guardar(com.uniesp.devops.preco.PrecoFornecedor precoFornecedor) {
        return repository.save(precoFornecedor);
    }
}