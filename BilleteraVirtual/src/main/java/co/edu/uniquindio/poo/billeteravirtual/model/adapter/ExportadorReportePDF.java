package co.edu.uniquindio.poo.billeteravirtual.model.adapter;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

public class ExportadorReportePDF implements ExportadorReporte {
    @Override
    public void exportarReporte(List<Transaccion> transacciones, String rutaArchivo) {
        PDDocument documento = new PDDocument();

        PDPage pagina = new PDPage();
        documento.addPage(pagina);

        try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina)) {
            contenido.beginText();
            contenido.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contenido.newLineAtOffset(25, 750);

            // Escribir el encabezado
            contenido.showText("ID Transacción | Fecha | Tipo | Monto | Descripción");
            contenido.newLine();

            // Escribir los datos de las transacciones
            contenido.setFont(PDType1Font.HELVETICA, 10);
            for (Transaccion transaccion : transacciones) {
                contenido.showText(transaccion.getIdTransaccion() + " | " +
                        transaccion.getFecha() + " | " +
                        transaccion.getTipo() + " | " +
                        transaccion.getMonto() + " | " +
                        transaccion.getDescripcion());
                contenido.newLine();
            }

            contenido.endText();
        } catch (IOException e) {
            System.err.println("Error al generar el reporte PDF: " + e.getMessage());
        }

        try {
            documento.save(rutaArchivo);
            documento.close();
            System.out.println("Reporte PDF generado correctamente: " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar el reporte PDF: " + e.getMessage());
        }
    }
}
