/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.lib;

import java.util.List;
import pe.edu.utp.poo.application.common.Util;
import pe.edu.utp.poo.application.db.Database;
import pe.edu.utp.poo.application.pojo.Usuario;

/**
 *
 * @author manuelguarniz
 */
public class UsuarioLogica implements ICRUDLogica<Usuario> {
    private final Database db;
    
    public UsuarioLogica(Database db) {
        this.db = db;
    }
    
    @Override
    public List<Usuario> listar() {
        return this.db.listaUsuarios();
    }
    
    @Override
    public Usuario obtenerPorId(String id) {
        return this.db.obtenerUsuario(id);
    }
    
    @Override
    public Usuario guardar(Usuario usuario) throws RuntimeException {
        if (usuario.getId() == null || "".equalsIgnoreCase(usuario.getId())) {
            usuario.setUsuarioAcceso(autogenerarUsuario(usuario));
            usuario.setClaveAcceso(Util.generateShortUUID());
            return this.db.agregarUsuario(usuario);
        } else {
            boolean resultado = this.db.modificarUsuario(usuario.getId(), usuario);
            if (resultado) {
                return usuario;
            } else {
                throw new RuntimeException("Usuario no existe");
            }
        }
    }
    
    @Override
    public boolean eliminar(String id) {
        Usuario usuario = this.db.obtenerUsuario(id);
        if (usuario == null) {
            throw new RuntimeException("Usuario no existe");
        }
        return this.db.eliminarUsuario(usuario);
    }
    
    private String autogenerarUsuario(Usuario usuario) {
        String primerNombre = usuario.getNombres().trim().split(" ")[0];
        String primerApellido = usuario.getApellidos().trim().split(" ")[0];
        
        return (primerNombre + "." + primerApellido).toLowerCase();
    }
}
