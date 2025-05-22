package co.edu.uniquindio.poo.billeteravirtual.model.servicios;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ServicioUsuario {

    /**
     * Instancia única del singleton que maneja los usuarios registrados.
     */
    private static ServicioUsuario instancia;

    /**
     * Lista de usuarios registrados.
     */
    public List<Usuario> usuariosRegistrados = new ArrayList<>();

    /**
     * Constructor privado para evitar instanciación externa.
     */
    private ServicioUsuario() {}

    /**
     * Obtiene la instancia única de ServicioUsuario.
     * @return instancia singleton de ServicioUsuario
     */
    public static ServicioUsuario getInstancia() {
        if (instancia == null) {
            instancia = new ServicioUsuario();
        }
        return instancia;
    }

    /**
     * Registra un nuevo usuario si no existe previamente.
     * @param nombre Nombre del usuario
     * @param correo Correo electrónico
     * @param telefono Número telefónico
     * @param cedula Cédula de identidad
     * @param palabraClave Palabra clave de seguridad
     * @param clave Clave de acceso
     */
    public void registrarUsuario(String nombre, String correo, String telefono, String cedula, String palabraClave, String clave){
        if(!verificarExistenciaUsuario(cedula)) {
            Usuario usuario = new Usuario.UsuarioBuilder()
                    .Nombre(nombre)
                    .Cedula(cedula)
                    .Telefono(telefono)
                    .Correo(correo)
                    .PalabraClave(palabraClave)
                    .ClaveAcceso(clave)
                    .build();

            usuariosRegistrados.add(usuario);
        } else {
            System.out.println("Usuario ya existe");
        }
    }

    /**
     * Verifica si un usuario con la cédula dada ya existe.
     * @param cedula Cédula a verificar
     * @return true si el usuario existe, false si no
     */
    public boolean verificarExistenciaUsuario(String cedula) {
        for(Usuario u: usuariosRegistrados){
            if(u.getCedula().equals(cedula)){
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene un usuario por su cédula.
     * @param cedula Cédula del usuario
     * @return Usuario encontrado o null si no existe
     */
    public Usuario obtenerUsuario(String cedula)  {
        for(Usuario usuario : usuariosRegistrados){
            if(usuario.getCedula().equals(cedula)){
                return usuario;
            }
        }
        return null;
    }

    /**
     * Elimina un usuario por cédula.
     * @param cedula Cédula del usuario a eliminar
     */
    public void eliminarUsuario(String cedula) {
        usuariosRegistrados.removeIf(u -> u.getCedula().equals(cedula));
    }

    /**
     * Autentica un usuario verificando cédula y clave de acceso.
     * @param cedula Cédula del usuario
     * @param claveAcceso Clave de acceso
     * @return true si las credenciales son válidas, false en caso contrario
     */
    public boolean autenticarUsuario(String cedula, String claveAcceso) {
        for (Usuario u : usuariosRegistrados) {
            if (u.getCedula().equals(cedula) && u.getClaveAcceso().equals(claveAcceso)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Actualiza la clave de acceso y palabra clave de un usuario existente.
     * @param usuarioActualizado Usuario con los datos actualizados
     */
    public void actualizarUsuario(Usuario usuarioActualizado) {
        for (Usuario u : usuariosRegistrados) {
            if (u.getCedula().equals(usuarioActualizado.getCedula())) {
                u.setClaveAcceso(usuarioActualizado.getClaveAcceso());
                u.setPalabraclave(usuarioActualizado.getPalabraclave());
                break;
            }
        }
    }

    public List<Usuario> getUsuariosRegistrados() {
        return usuariosRegistrados;
    }

    public ServicioUsuario setUsuariosRegistrados(List<Usuario> usuariosRegistrados) {
        this.usuariosRegistrados = usuariosRegistrados;
        return this;
    }
}
