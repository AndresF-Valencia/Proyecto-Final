package co.edu.uniquindio.poo.billeteravirtual.datos;

import co.edu.uniquindio.poo.billeteravirtual.entidades.Presupuesto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DataPersistence {
    public static void savePresupuesto(Presupuesto presupuesto) {
        Properties properties = new Properties();
        properties.setProperty("idPresupuesto", String.valueOf(presupuesto.getIdPresupuesto()));
        properties.setProperty("descripcion", presupuesto.getDescripcion());
        properties.setProperty("montoTotal", String.valueOf(presupuesto.getMontoTotal()));
        properties.setProperty("montoGastado", String.valueOf(presupuesto.getMontoGastado()));

        try (FileOutputStream outputStream = new FileOutputStream("presupuesto.properties")) {
            properties.store(outputStream, "Presupuesto Data");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Presupuesto loadPresupuesto() {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream("presupuesto.properties")) {
            properties.load(inputStream);

            int idPresupuesto = Integer.parseInt(properties.getProperty("idPresupuesto"));
            String descripcion = properties.getProperty("descripcion");
            double montoTotal = Double.parseDouble(properties.getProperty("montoTotal"));
            double montoGastado = Double.parseDouble(properties.getProperty("montoGastado"));

            return new Presupuesto(idPresupuesto, descripcion, montoTotal, montoGastado);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
