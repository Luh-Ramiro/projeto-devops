package com.uniesp.devops.precofornecedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/precos")
public class PrecoFornecedorController {

    @Autowired
    private PrecoFornecedorService service;

    @GetMapping
    public List<com.uniesp.devops.preco.PrecoFornecedor> listar() {
        return service.listarTodos();
    }

    @PostMapping
    public com.uniesp.devops.preco.PrecoFornecedor criar(@RequestBody com.uniesp.devops.preco.PrecoFornecedor precoFornecedor) {
        return service.guardar(precoFornecedor);
    }
}