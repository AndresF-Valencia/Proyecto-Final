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
            contenido.newLine();
            contenido.setFont(PDType1Font.HELVETICA, 12);


            if (estadisticas != null) {
                contenido.newLine();
                contenido.showText("Usuario con más transacciones: " + estadisticas.usuarioMasActivo);
                contenido.newLine();
                contenido.showText("Categoría más usada: " + estadisticas.categoriaMasUsada);
                contenido.newLine();
            }

            if (totalIngresosCliente != null && totalGastosCliente != null) {
                contenido.newLine();
                contenido.showText(String.format("Total Ingresos: %.2f", totalIngresosCliente));
                contenido.newLine();
                contenido.showText(String.format("Total Gastos: %.2f", totalGastosCliente));
                contenido.newLine();
            }


            contenido.newLine();
            contenido.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contenido.showText("ID | Fecha | Tipo | Monto | Descripción");
            contenido.newLine();

            contenido.setFont(PDType1Font.HELVETICA, 10);
            for (Transaccion t : transacciones) {
                contenido.showText(t.getIdTransaccion() + " | " + t.getFecha() + " | " +
                        t.getTipo() + " | " + t.getMonto() + " | " + t.getDescripcion());
                contenido.newLine();
            }

            contenido.endText();

        } catch (IOException e) {
            System.err.println("Error al escribir el PDF: " + e.getMessage());
        }

        try {
            documento.save(rutaArchivo);
            documento.close();
        } catch (IOException e) {
            System.err.println("Error al guardar el PDF: " + e.getMessage());
        }
    }
}