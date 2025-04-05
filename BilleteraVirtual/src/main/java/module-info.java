module co.edu.uniquindio.poo.billeteravirtual {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.poo.billeteravirtual to javafx.fxml;
    exports co.edu.uniquindio.poo.billeteravirtual;
    exports co.edu.uniquindio.poo.billeteravirtual.entidades;
    opens co.edu.uniquindio.poo.billeteravirtual.entidades to javafx.fxml;
    exports co.edu.uniquindio.poo.billeteravirtual.utilidades;
    opens co.edu.uniquindio.poo.billeteravirtual.utilidades to javafx.fxml;
}