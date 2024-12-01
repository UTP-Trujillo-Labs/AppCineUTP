package pe.edu.utp.poo.application.model;


public class Usuario extends Persona {
    private int usuarioId;
    private String rol;
    private short edad;

    public Usuario() {}
    public Usuario(String nombres, String apellidos, String numeroDocumento, String rol, short edad) {
        super(nombres, apellidos, numeroDocumento);
        this.rol = rol;
        this.edad = edad;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public short getEdad() {
        return edad;
    }

    public void setEdad(short edad) {
        this.edad = edad;
    }
}
