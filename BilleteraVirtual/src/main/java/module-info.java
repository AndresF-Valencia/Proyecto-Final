module co.edu.uniquindio.poo.billeteravirtual {
    exports co.edu.uniquindio.poo.billeteravirtual.model.servicios;
    exports co.edu.uniquindio.poo.billeteravirtual.model.entidades;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.apache.pdfbox;
    requires junit;


    opens co.edu.uniquindio.poo.billeteravirtual to javafx.fxml;
    opens co.edu.uniquindio.poo.billeteravirtual.viewControllers to javafx.fxml;
    opens co.edu.uniquindio.poo.billeteravirtual.test to junit;
    exports co.edu.uniquindio.poo.billeteravirtual;
}
