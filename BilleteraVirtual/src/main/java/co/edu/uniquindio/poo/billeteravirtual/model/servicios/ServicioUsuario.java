package co.edu.uniquindio.poo.billeteravirtual.model.servicios;

import co.edu.uniquindio.poo.billeteravirtual.model.entidades.Usuario;
import co.edu.uniquindio.poo.billeteravirtual.model.utilidades.Logger;

import java.util.ArrayList;
import java.util.List;

public class ServicioUsuario {
    private static ServicioUsuario instance = new ServicioUsuario();
    public List<Usuario> usuariosRegistrados = new ArrayList<>();

    // Registrar un nuevo usuario
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
        } else{
            System.out.println("Usuario ya existe");
        }
    }

    public boolean verificarExistenciaUsuario(String cedula) {
        boolean existe = false;
        for(Usuario u: usuariosRegistrados){
            if(u.getCedula().equals(cedula)){
                existe = true;
            }
        }
        return existe;
    }

    // Obtener un usuario por cédula
    public Usuario obtenerUsuario(String cedula)  {
        Usuario usuarioEncontrado = null;
        for(Usuario usuario : usuariosRegistrados){
            if(usuario.getCedula().equals(cedula)){
                usuarioEncontrado = usuario;
            }
        }
        return usuarioEncontrado;
    }

    // Eliminar un usuario
    public void eliminarUsuario(String cedula) {

        System.out.println("Usuario eliminado exitosamente.");
    }


    // Verificar login de usuario
    public boolean autenticarUsuario(String cedula, String claveAcceso) {
        boolean autenticado = false;
        for (Usuario u : usuariosRegistrados) {
            String cedulaUsuario = u.getCedula();
            String claveUsuario = u.getClaveAcceso();
            if (cedulaUsuario.equals(cedula) && claveUsuario.equals(claveAcceso)) {
                autenticado = true;
                break;
            }
        }
        return autenticado;
    }

    public void actualizarUsuario(Usuario usuarioActualizado) {
        for (Usuario u : usuariosRegistrados) {
            if (u.getCedula().equals(usuarioActualizado.getCedula())) {
                u.setClaveAcceso(usuarioActualizado.getClaveAcceso());
                u.setPalabraclave(usuarioActualizado.getPalabraclave());
                break;
            }
        }
    }

    private ServicioUsuario() {}
    public static ServicioUsuario getInstancia() {
        if (instance == null) {
            instance = new ServicioUsuario();
        }
        return instance;
    }
    public List<Usuario> getUsuariosRegistrados() {
        return usuariosRegistrados;
    }

    public ServicioUsuario setUsuariosRegistrados(List<Usuario> usuariosRegistrados) {
        this.usuariosRegistrados = usuariosRegistrados;
        return this;
    }
}
