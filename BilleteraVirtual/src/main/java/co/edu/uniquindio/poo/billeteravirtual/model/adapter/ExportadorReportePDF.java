package co.edu.uniquindio.poo.billeteravirtual.model.adapter;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Transaccion;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

/**
 * Implementación de {@link ExportadorReporte} que permite exportar
 * las transacciones en formato PDF utilizando PDFBox.
 */
public class ExportadorReportePDF implements ExportadorReporte {

    /**
     * Estadísticas generales del sistema.
     */
    private EstadisticasReporte estadisticas;

    /**
     * Total de ingresos del cliente.
     */
    private Double totalIngresosCliente;

    /**
     * Total de gastos del cliente.
     */
    private Double totalGastosCliente;

    /**
     * Establece las estadísticas generales del sistema.
     *
     * @param estadisticas Instancia de {@link EstadisticasReporte}.
     */
    public void setEstadisticas(EstadisticasReporte estadisticas) {
        this.estadisticas = estadisticas;
    }

    /**
     * Establece los totales de ingresos y gastos del cliente.
     *
     * @param ingresos Total de ingresos.
     * @param gastos Total de gastos.
     */
    public void setTotalesCliente(Double ingresos, Double gastos) {
        this.totalIngresosCliente = ingresos;
        this.totalGastosCliente = gastos;
    }

    /**
     * Exporta un reporte en formato PDF con la información de las transacciones,
     * estadísticas generales y los totales del cliente.
     *
     * @param transacciones Lista de transacciones.
     * @param rutaArchivo Ruta donde se guardará el archivo PDF.
     */
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
