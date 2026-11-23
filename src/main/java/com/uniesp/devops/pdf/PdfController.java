package com.uniesp.devops.pdf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    private static final Logger log = LoggerFactory.getLogger(PdfController.class);

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file,
                                            @RequestParam("fornecedorId") Long fornecedorId) {

        log.info("Recebendo solicitação de importação de PDF para o fornecedor ID: {}", fornecedorId);

        if (file.isEmpty()) {
            log.warn("Tentativa de importação com arquivo vazio.");
            return ResponseEntity.badRequest()
                    .body("Por favor, envie um ficheiro PDF válido.");
        }

        log.info("Arquivo recebido: {} | Tamanho: {} bytes",
                file.getOriginalFilename(),
                file.getSize());

        String resultado = pdfService.importarPrecos(file, fornecedorId);

        log.info("Solicitação de importação de PDF finalizada.");

        return ResponseEntity.ok(resultado);
    }
}
