package com.uniesp.devops.pdf;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PdfService {

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
}