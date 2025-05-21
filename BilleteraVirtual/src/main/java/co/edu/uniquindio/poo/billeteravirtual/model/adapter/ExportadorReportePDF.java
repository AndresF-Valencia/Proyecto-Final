package co.edu.uniquindio.poo.billeteravirtual.model.adapter;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.ReporteCliente;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;
public class ExportadorReportePDF implements ExportadorReporte {

    private EstadisticasReporte estadisticas;
    private Double totalIngresosCliente;
    private Double totalGastosCliente;

    public void setEstadisticas(EstadisticasReporte estadisticas) {
        this.estadisticas = estadisticas;
    }
    public void setTotalesCliente(Double ingresos, Double gastos) {
        this.totalIngresosCliente = ingresos;
        this.totalGastosCliente = gastos;
    }
    @Override
    public void exportarReporte(List<Transaccion> transacciones, String rutaArchivo) {
        PDDocument documento = new PDDocument();
        PDPage pagina = new PDPage();
        documento.addPage(pagina);

        try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina)) {
            contenido.beginText();
            contenido.setFont(PDType1Font.HELVETICA_BOLD, 14);
            contenido.newLineAtOffset(25, 750);
            contenido.showText("Reporte de Transacciones");

            contenido.setFont(PDType1Font.HELVETICA, 12);
            contenido.newLineAtOffset(0, -20);

            if (estadisticas != null) {
                contenido.showText("Usuario con más transacciones: " + estadisticas.usuarioMasActivo);
                contenido.newLineAtOffset(0, -15);
                contenido.showText("Categoría más usada: " + estadisticas.categoriaMasUsada);
                contenido.newLineAtOffset(0, -15);
            }

            if (totalIngresosCliente != null && totalGastosCliente != null) {
                contenido.showText(String.format("Total Ingresos: %.2f", totalIngresosCliente));
                contenido.newLineAtOffset(0, -15);
                contenido.showText(String.format("Total Gastos: %.2f", totalGastosCliente));
                contenido.newLineAtOffset(0, -20);
            }

            contenido.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contenido.showText("ID | Fecha | Tipo | Monto | Descripción");
            contenido.newLineAtOffset(0, -15);

            contenido.setFont(PDType1Font.HELVETICA, 10);
            for (Transaccion t : transacciones) {
                String linea = t.getIdTransaccion() + " | " +
                        t.getFecha() + " | " +
                        t.getTipo() + " | " +
                        String.format("%.2f", t.getMonto()) + " | " +
                        t.getDescripcion();
                contenido.showText(linea);
                contenido.newLineAtOffset(0, -12);
            }

            contenido.endText();
        } catch (IOException e) {
            System.err.println("Error al escribir el PDF: " + e.getMessage());
        }

        try {
            documento.save(rutaArchivo);
            documento.close();
            System.out.println("PDF generado correctamente en: " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar el PDF: " + e.getMessage());
        }
    }
}