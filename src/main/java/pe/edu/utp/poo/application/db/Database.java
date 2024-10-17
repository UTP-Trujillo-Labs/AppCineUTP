/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.db;

import java.util.ArrayList;
import java.util.List;
import pe.edu.utp.poo.application.common.Util;
import pe.edu.utp.poo.application.enums.RolesEnum;
import pe.edu.utp.poo.application.pojo.Usuario;

/**
 *
 * @author manuelguarniz
 */
public class Database {
    
    private final List<Usuario> dbUsuarios;
    
    public Database() {
        dbUsuarios = new ArrayList<>();
        this.loadData();
    }
    
    private void loadData() {
        Usuario usuario1 = new Usuario("Manuel", "Guarniz", "71850001", 29, RolesEnum.Admin, true);
        Usuario usuario2 = new Usuario("Esteban", "Cruz", "71850002", 30, RolesEnum.Vendedor, true);
        Usuario usuario3 = new Usuario("Juan", "Perez", "71850003", 31, RolesEnum.Vendedor, false);
        this.agregarUsuario(usuario1);
        this.agregarUsuario(usuario2);
        this.agregarUsuario(usuario3);
    }
    
    public List<Usuario> listaUsuarios() {
        return this.dbUsuarios;
    }
    
    public Usuario obtenerUsuario(String id) {
        for (Usuario u : this.dbUsuarios) {
            if (u.getId().equalsIgnoreCase(id)) {                
                return u;
            }
        }
        return null;
    }
    
    public boolean modificarUsuario(String id, Usuario usuario) {
        Usuario u = this.obtenerUsuario(id);
        if (u != null) {
            u.setNombres(usuario.getNombres());
            u.setApellidos(usuario.getApellidos());
            u.setDni(usuario.getDni());
            u.setEdad(usuario.getEdad());
            u.setRol(usuario.getRol());
            u.setEstado(usuario.isEstado());
            return true;
        }
        return false;
    }
    
    public Usuario agregarUsuario(Usuario usuario) {
        usuario.setId(Util.getID());
        dbUsuarios.add(usuario);
        return usuario;
    }
    
    public boolean eliminarUsuario(Usuario usuario) {
        return dbUsuarios.remove(usuario);
    }
}
