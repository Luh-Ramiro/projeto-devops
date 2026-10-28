package com.uniesp.devops.precofornecedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrecoFornecedorService {

    @Autowired
    private PrecoFornecedorRepository repository;

    public List<PrecoFornecedor> listarTodos() {
        return repository.findAll();
    }

    public PrecoFornecedor guardar(PrecoFornecedor precoFornecedor) {
        return repository.save(precoFornecedor);
    }
}