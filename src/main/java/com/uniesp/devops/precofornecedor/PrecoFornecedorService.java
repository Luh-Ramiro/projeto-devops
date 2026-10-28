package com.uniesp.devops.precofornecedor;

import com.uniesp.devops.precofornecedor.PrecoFornecedor;
import com.uniesp.devops.produto.Produto;
import com.uniesp.devops.fornecedor.Fornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class PrecoFornecedorService {

    @Autowired
    private PrecoFornecedorRepository repository;

    public String compararEProcessarPreco(Produto produto, Fornecedor fornecedor, BigDecimal precoAtual) {
        // 1. Busca o último preço registado para este produto e fornecedor
        Optional<PrecoFornecedor> ultimoPrecoOpt = repository.findTopByProdutoAndFornecedorOrderByDataImportacaoDesc(produto, fornecedor);

        // 2. Se não existir histórico, classifica como NOVO
        if (ultimoPrecoOpt.isEmpty()) {
            return "NOVO";
        }

        BigDecimal precoAnterior = BigDecimal.valueOf(ultimoPrecoOpt.get().getPrecoUnitario());

        // 3. Validação de preços inválidos ou nulos
        if (precoAnterior == null || precoAnterior.compareTo(BigDecimal.ZERO) <= 0 ||
                precoAtual == null || precoAtual.compareTo(BigDecimal.ZERO) <= 0) {
            return "PENDENTE/INVÁLIDO";
        }

        // 4. Cálculo da variação percentual: ((atual - anterior) / anterior) * 100
        BigDecimal variacao = precoAtual.subtract(precoAnterior)
                .divide(precoAnterior, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        // 5. Classificação baseada no resultado da variação
        if (variacao.compareTo(BigDecimal.ZERO) > 0) {
            return "Aumentou (" + variacao + "%)";
        } else if (variacao.compareTo(BigDecimal.ZERO) < 0) {
            return "Diminuiu (" + variacao + "%)";
        } else {
            return "Sem alteração";
        }
      }
    }

