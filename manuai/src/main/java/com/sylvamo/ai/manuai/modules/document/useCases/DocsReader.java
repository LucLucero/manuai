package com.sylvamo.ai.manuai.modules.document.useCases;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.List;

@Component
public class DocsReader {

    public String documentSource() {
        final String[] filePath = {""};

        SwingUtilities.invokeLater(() -> {
            JFileChooser fileChooser = new JFileChooser();
            int userSelection = fileChooser.showOpenDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                filePath[0] = fileChooser.getSelectedFile().getAbsolutePath();
            }
        });

        // Espera um tempo para a seleção ser feita
        try {
            Thread.sleep(5000); // Ajuste conforme necessário
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return filePath[0];
    }

    public List<Document> getDocsFromPdf() {
        String filePath = documentSource();
        PagePdfDocumentReader pagePdfDocumentReader = new PagePdfDocumentReader(filePath,
                PdfDocumentReaderConfig.builder()
                        .withPageTopMargin(0)
                        .withPageExtractedTextFormatter(ExtractedTextFormatter.builder().withNumberOfTopTextLinesToDelete(0).build())
                        .withPagesPerDocument(1)
                        .build());
        return pagePdfDocumentReader.read();
    }
}
