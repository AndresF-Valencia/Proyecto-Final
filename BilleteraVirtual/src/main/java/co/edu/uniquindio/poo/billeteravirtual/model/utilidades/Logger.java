package co.edu.uniquindio.poo.billeteravirtual.model.utilidades;
import javafx.animation.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Logger {
    private static Logger instance;
    private Logger() {}

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }


    public void mostrarToast(AnchorPane rootPane, String mensaje) {
        Label label = new Label(mensaje);
        label.setStyle("""
            -fx-background-color: #333;
            -fx-text-fill: white;
            -fx-padding: 10 20 10 20;
            -fx-background-radius: 10;
            -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 2);
        """);

        StackPane toast = new StackPane(label);
        toast.setOpacity(0);
        toast.setMouseTransparent(true);

        AnchorPane.setRightAnchor(toast, 20.0);
        AnchorPane.setBottomAnchor(toast, 20.0);

        rootPane.getChildren().add(toast);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.4), toast);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        PauseTransition stay = new PauseTransition(Duration.seconds(5));

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), toast);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        SequentialTransition seq = new SequentialTransition(fadeIn, stay, fadeOut);
        seq.setOnFinished(e -> rootPane.getChildren().remove(toast));
        seq.play();
    }
}
