module co.edu.uniquindio.poo.billeteravirtual {
    exports co.edu.uniquindio.poo.billeteravirtual.model.servicios;
    exports co.edu.uniquindio.poo.billeteravirtual.model.entidades;
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires java.desktop;
    requires org.apache.pdfbox;


    opens co.edu.uniquindio.poo.billeteravirtual to javafx.fxml;
    opens co.edu.uniquindio.poo.billeteravirtual.viewControllers to javafx.fxml;
    exports co.edu.uniquindio.poo.billeteravirtual;
}
