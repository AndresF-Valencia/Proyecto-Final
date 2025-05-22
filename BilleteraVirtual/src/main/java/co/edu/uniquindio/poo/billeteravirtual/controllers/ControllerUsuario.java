package co.edu.uniquindio.poo.billeteravirtual.controllers;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Admin;
import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.servicios.ServicioUsuario;
import co.edu.uniquindio.poo.billeteravirtual.viewControllers.ViewUsuario;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.*;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Controlador para gestionar la interacción entre la vista de usuario y el modelo de usuarios.
 * <p>
 * Este controlador administra la navegación entre las diferentes vistas de usuario (inicio,
 * registro, recuperación), maneja el registro e inicio de sesión, la recuperación de claves
 * mediante código de verificación, y la transición entre escenas.
 * </p>
 */
public class ControllerUsuario {
    private String codigoGenerado;
    private final ViewUsuario view;
    private final ServicioUsuario servicioUsuario;

    /**
     * Constructor que inicializa el controlador con la vista del usuario y el servicio correspondiente.
     *
     * @param view instancia de la vista de usuario para manipular sus componentes
     */
    public ControllerUsuario(ViewUsuario view) {
        this.view = view;
        this.servicioUsuario = ServicioUsuario.getInstancia();
    }

    /**
     * Cambia la vista activa dentro de la interfaz del usuario según el nombre de la vista.
     *
     * @param vistaActiva nombre de la vista que se quiere mostrar ("Bienvenida", "IniciarSesion", "RegistroUsuario", "RecuperarUsuario")
     */
    private void cambiarVista(String vistaActiva) {
        view.getBienvenida().setVisible(vistaActiva.equals("Bienvenida"));
        view.getIniciarSesion().setVisible(vistaActiva.equals("IniciarSesion"));
        view.getRegistroUsuario().setVisible(vistaActiva.equals("RegistroUsuario"));
        view.getRecuperar().setVisible(vistaActiva.equals("RecuperarUsuario"));
    }

    /** Muestra el formulario para registrar un nuevo usuario. */
    public void mostrarRegistro() {
        cambiarVista("RegistroUsuario");
        view.getDatosUsuario().setVisible(true);
    }

    /** Muestra la vista para recuperación de contraseña mediante palabra clave. */
    public void mostrarRecuperacion() {
        cambiarVista("RecuperarUsuario");
        view.getPanePalabraClave().setVisible(true);
    }

    /** Muestra la vista de inicio de sesión. */
    public void mostrarInicioSesion() {
        cambiarVista("IniciarSesion");
    }

    /**
     * Finaliza el proceso de registro validando campos y agregando el nuevo usuario al sistema.
     * Muestra mensajes de error en caso de campos vacíos o contraseñas no coincidentes.
     */
    public void finalizarRegistro() {
        String nombre = view.getTextNombre().getText();
        String correo = view.getTextCorreo().getText();
        String telefono = view.getTextTelefono().getText();
        String cedula = view.getTextCedula().getText();
        String palabraClave = view.getTextPalabraClave().getText();
        String clave = view.getClave().getText();
        String verificarClave = view.getVerificarClave().getText();

        if (camposVacios(nombre, correo, telefono, cedula, palabraClave, clave, verificarClave)) {
            Logger.getInstance().mostrarToast(view.rootPane, "Por favor llene todos los campos");
            return;
        }

        if (mostrarErrorSi(clave == null || !clave.equals(verificarClave), "Las contraseñas no coinciden")) return;

        servicioUsuario.registrarUsuario(nombre, correo, telefono, cedula, palabraClave, clave);
        limpiarCampos();

        view.getPonerContrasena().setVisible(false);

        Logger.getInstance().mostrarToast(view.rootPane, "Usuario registrado con éxito");
        cambiarVista("Bienvenida");
    }

    /** Continúa el flujo de registro mostrando el formulario para establecer la contraseña. */
    public void continuar() {
        view.getDatosUsuario().setVisible(false);
        view.getPonerContrasena().setVisible(true);
    }

    /**
     * Realiza la autenticación del usuario mediante cédula y clave.
     * En caso de éxito inicia sesión, en caso contrario muestra mensaje de error.
     *
     * @return true si la autenticación fue exitosa, false en caso contrario
     */
    public boolean iniciarSesion() {
        String cedula = view.getCedulaIS().getText();
        String clave = view.getClaveIS().getText();

        boolean autenticado = servicioUsuario.autenticarUsuario(cedula, clave);
        if (autenticado) {
            Usuario usuarioEncontrado = servicioUsuario.obtenerUsuario(cedula);
            Sesion.getInstancia().iniciarSesion(usuarioEncontrado);
        } else {
            Logger.getInstance().mostrarToast(view.rootPane, "Clave y/o Cédula incorrecta");
        }

        return autenticado;
    }

    /**
     * Verifica si la palabra clave ingresada corresponde a un usuario registrado,
     * genera un código de recuperación y lo asigna al usuario.
     */
    public void verificarPalabraClave() {
        String palabraclave = view.getTextRecuperar().getText();
        for (Usuario u : servicioUsuario.getUsuariosRegistrados()) {
            String palabra = u.getPalabraclave();
            if (palabra.equals(palabraclave)) {
                generarCodigoRecuperacion();
                u.setClaveAcceso(codigoGenerado);
                view.getPanePalabraClave().setVisible(false);
                view.getPaneCodigo().setVisible(true);
                break;
            }
        }
    }

    /**
     * Verifica si el código ingresado para recuperación de contraseña es correcto.
     *
     * @return true si el código coincide, false en caso contrario
     */
    public boolean verificarCodigo() {
        String codigoIngresado = view.getTextCodigo().getText();
        if (codigoGenerado != null && codigoGenerado.equals(codigoIngresado)) {
            Logger.getInstance().mostrarToast(view.rootPane, "✅ Código correcto.");
            cambiarVista("IniciarSesion");
            return true;
        } else {
            Logger.getInstance().mostrarToast(view.rootPane, "❌ Código incorrecto.");
            cambiarVista("Bienvenida");
            Logger.getInstance().mostrarToast(view.rootPane, "No se puede recuperar el acceso a la cuenta.");
        }
        return false;
    }

    /** Genera un código aleatorio para la recuperación de contraseña y lo muestra en un toast. */
    public void generarCodigoRecuperacion() {
        codigoGenerado = new GeneradorCodigo().generarCodigo();
        Logger.getInstance().mostrarToast(view.rootPane, "Código de recuperación: " + codigoGenerado);
    }

    /**
     * Cambia la escena actual a una nueva cargada desde un archivo FXML.
     * Aplica una animación de fade-in durante la transición.
     *
     * @param stage     escenario donde se debe cambiar la escena
     * @param rutaFXML  ruta del archivo FXML para cargar la nueva escena
     */
    public void cambiarEscena(Stage stage, String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            fadeIn.play();
        } catch (IOException e) {
            Logger.getInstance().mostrarToast(view.rootPane, "Cédula y/o clave incorrecta");
        }
    }

    /**
     * Cambia la escena actual para el administrador a una nueva cargada desde un archivo FXML.
     * Aplica una animación de fade-in durante la transición.
     *
     * @param stage     escenario donde se debe cambiar la escena
     * @param rutaFXML  ruta del archivo FXML para cargar la nueva escena
     */
    public void cambiarEscenaAdmin(Stage stage, String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            fadeIn.play();
        } catch (IOException e) {
            Logger.getInstance().mostrarToast(view.rootPane, "Cédula y/o clave incorrecta");
        }
    }

    /**
     * Inicializa datos de prueba con algunos usuarios y administradores si no existen previamente.
     */
    public void iniciarDatos() {
        if (!servicioUsuario.verificarExistenciaUsuario("1028")) {
            Usuario usuario = new Usuario.UsuarioBuilder()
                    .Nombre("Andres")
                    .Cedula("1028")
                    .Telefono("321")
                    .Correo("andres@gmail.com")
                    .PalabraClave("Pantera")
                    .ClaveAcceso("1021")
                    .build();
            Usuario usuario1 = new Usuario.UsuarioBuilder()
                    .Nombre("Pablo")
                    .Cedula("1092")
                    .Telefono("123")
                    .Correo("juan@gmail.com")
                    .PalabraClave("marcelo")
                    .ClaveAcceso("1234")
                    .build();
            Admin admin = (Admin) new Admin.AdminBuilder()
                    .Nombre("admin")
                    .Cedula("123")
                    .Telefono("40029592")
                    .Correo("admin@gmail.com")
                    .PalabraClave("admin")
                    .ClaveAcceso("123")
                    .build();

            servicioUsuario.getUsuariosRegistrados().add(admin);
            servicioUsuario.getUsuariosRegistrados().add(usuario);
            servicioUsuario.getUsuariosRegistrados().add(usuario1);
        }
    }

    /** Limpia los campos de texto del formulario de registro. */
    private void limpiarCampos() {
        view.getTextNombre().clear();
        view.getTextCorreo().clear();
        view.getTextTelefono().clear();
        view.getTextCedula().clear();
        view.getTextPalabraClave().clear();
        view.getClave().clear();
        view.getVerificarClave().clear();
    }

    /**
     * Muestra un mensaje de error en un toast si la condición es verdadera.
     *
     * @param condicion condición que determina si se muestra el error
     * @param mensaje   mensaje a mostrar en caso de error
     * @return true si se mostró el error, false si no
     */
    private boolean mostrarErrorSi(boolean condicion, String mensaje) {
        if (condicion) {
            Logger.getInstance().mostrarToast(view.rootPane, mensaje);
            return true;
        }
        return false;
    }

    /**
     * Verifica si alguno de los parámetros de texto está vacío o es nulo.
     *
     * @param campos variables String a verificar
     * @return true si al menos un campo está vacío o nulo, false si todos contienen texto
     */
    private boolean camposVacios(String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public void volverBienvenida(){
        view.Bienvenida.setVisible(true);
        view.IniciarSesion.setVisible(false);
    }
}

