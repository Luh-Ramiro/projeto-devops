package com.uniesp.devops.pdf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file,
                                            @RequestParam("fornecedorId") Long fornecedorId) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Por favor, envie um ficheiro PDF válido.");
        }

        String resultado = pdfService.importarPrecos(file, fornecedorId);
        return ResponseEntity.ok(resultado);
    }
}