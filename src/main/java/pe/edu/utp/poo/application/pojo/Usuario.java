/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.pojo;

import java.util.List;
import pe.edu.utp.poo.application.enums.RolesEnum;
import pe.edu.utp.poo.application.lib.UsuarioLogica;

/**
 *
 * @author manuelguarniz
 */
public class Usuario extends Persona implements Mantenimiento<Usuario>{
    
    private UsuarioLogica usuarioLogica;
    
    private String id;
    private RolesEnum rol;
    private String usuarioAcceso;
    private String claveAcceso;
    private boolean estado;
    
    public Usuario() { }

    public Usuario(UsuarioLogica usuarioLogica) {
        this.usuarioLogica = usuarioLogica;
    }

    public Usuario(String nombres, String apellidos, String dni, int edad,
            String usuarioAcceso, String claveAcceso,
            RolesEnum rol, boolean estado) {
        super(nombres, apellidos, dni, edad);
        this.usuarioAcceso = usuarioAcceso;
        this.claveAcceso = claveAcceso;
        this.rol = rol;
        this.estado = estado;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioAcceso() {
        return usuarioAcceso;
    }

    public void setUsuarioAcceso(String usuarioAcceso) {
        this.usuarioAcceso = usuarioAcceso;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }
    
    public RolesEnum getRol() {
        return rol;
    }

    public void setRol(RolesEnum rol) {
        this.rol = rol;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public Usuario guardar() {
        return usuarioLogica.guardarUsuario(this);
    }
}
