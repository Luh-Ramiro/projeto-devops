package com.uniesp.devops.pdf;

import com.uniesp.devops.fornecedor.Fornecedor;
import com.uniesp.devops.fornecedor.FornecedorService;
import com.uniesp.devops.precofornecedor.PrecoFornecedor;
import com.uniesp.devops.precofornecedor.PrecoFornecedorService;
import com.uniesp.devops.produto.Produto;
import com.uniesp.devops.produto.ProdutoRepository;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PdfService {

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PrecoFornecedorService precoFornecedorService;

    public String extrairTextoDePdf(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            org.apache.pdfbox.pdmodel.PDDocument documento = Loader.loadPDF(bytes);

            PDFTextStripper stripper = new PDFTextStripper();
            String textoExtraido = stripper.getText(documento);

            documento.close();
            return textoExtraido;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar o ficheiro PDF: " + e.getMessage());
        }
    }

    public List<ItemPdf> extrairItens(String texto) {
        List<ItemPdf> itens = new ArrayList<>();
        int indicePreco = -1;
        boolean comReferencia = false;

        for (String linha : texto.split("\\R")) {
            String maiuscula = linha.toUpperCase();

            if (maiuscula.contains("FARDO")) {
                indicePreco = 1;
                comReferencia = maiuscula.contains("REF");
                continue;
            }
            if (maiuscula.contains("ATACADO")) {
                indicePreco = 0;
                comReferencia = false;
                continue;
            }
            if (indicePreco < 0) {
                continue;
            }

            String[] partes = linha.trim().split("\\s+");
            int n = partes.length;
            if (n < 3) {
                continue;
            }

            BigDecimal preco;
            try {
                String valor = (indicePreco == 0) ? partes[n - 2] : partes[n - 1];
                preco = new BigDecimal(valor.replace(",", "."));
            } catch (NumberFormatException e) {
                continue;
            }

            String referencia = null;
            String unidade = null;
            int inicioNome = 0;
            int fimNome = n - 2;

            if (comReferencia) {
                referencia = partes[0];
                unidade = partes[n - 3];
                inicioNome = 1;
                fimNome = n - 3;
            }

            String nome = String.join(" ", Arrays.copyOfRange(partes, inicioNome, fimNome));
            itens.add(new ItemPdf(referencia, nome, unidade, preco));
        }
        return itens;
    }

    public String importarPrecos(MultipartFile file, Long fornecedorId) {
        Fornecedor fornecedor = fornecedorService.buscarPorId(fornecedorId)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado: " + fornecedorId));

        String texto = extrairTextoDePdf(file);
        List<ItemPdf> itens = extrairItens(texto);

        int gravados = 0;
        List<String> semProduto = new ArrayList<>();

        for (ItemPdf item : itens) {
            Optional<Produto> produto = produtoRepository.findFirstByNomeIgnoreCase(item.nome());
            if (produto.isEmpty()) {
                semProduto.add(item.nome());
                continue;
            }

            PrecoFornecedor preco = new PrecoFornecedor();
            preco.setFornecedor(fornecedor);
            preco.setProduto(produto.get());
            preco.setPrecoUnitario(item.preco().doubleValue());
            preco.setDataImportacao(LocalDate.now());
            precoFornecedorService.guardar(preco);
            gravados++;
        }

        return gravados + " preço(s) gravado(s). Sem produto correspondente: " + semProduto;
    }
}