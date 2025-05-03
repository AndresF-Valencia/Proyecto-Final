module co.edu.uniquindio.poo.billeteravirtual {
    exports co.edu.uniquindio.poo.billeteravirtual.servicios;
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires java.desktop;


    opens co.edu.uniquindio.poo.billeteravirtual to javafx.fxml;
    opens co.edu.uniquindio.poo.billeteravirtual.viewControllers to javafx.fxml;
    exports co.edu.uniquindio.poo.billeteravirtual;
}
