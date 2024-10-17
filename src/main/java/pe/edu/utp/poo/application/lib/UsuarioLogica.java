/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.lib;

import java.util.List;
import pe.edu.utp.poo.application.db.Database;
import pe.edu.utp.poo.application.pojo.Usuario;

/**
 *
 * @author manuelguarniz
 */
public class UsuarioLogica {
    private Database db;
    
    public UsuarioLogica() {
        this.db = new Database();
    }
    
    public List<Usuario> listaUsuarios() {
        return this.db.listaUsuarios();
    }
    
    public Usuario getUsuario(String id) {
        return this.db.obtenerUsuario(id);
    }
    
    public Usuario guardarUsuario(Usuario usuario) throws RuntimeException {
        if (this.db.obtenerUsuario(usuario.getId()) != null) {
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
    
    public boolean elimiarUsuario(String id) {
        Usuario usuario = this.db.obtenerUsuario(id);
        if (usuario == null) {
            throw new RuntimeException("Usuario no existe");
        }
        return this.db.eliminarUsuario(usuario);
    }
}
