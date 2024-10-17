/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.pojo;

import pe.edu.utp.poo.application.enums.RolesEnum;

/**
 *
 * @author manuelguarniz
 */
public class Usuario extends Persona {
    private String id;
    private RolesEnum rol;
    private boolean estado;
    
    public Usuario() { }

    public Usuario(RolesEnum rol, boolean estado) {
        this.rol = rol;
        this.estado = estado;
    }

    public Usuario(String nombres, String apellidos, String dni, int edad, RolesEnum rol, boolean estado) {
        super(nombres, apellidos, dni, edad);
        this.rol = rol;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    
    
}
