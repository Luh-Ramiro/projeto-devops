package com.uniesp.devops.pdf;

import java.math.BigDecimal;

public record ItemPdf(String referencia, String nome, String unidade, BigDecimal preco) {
}