package pe.edu.utp.poo.application.model;


public class Usuario extends Persona {
    private Integer usuarioId;
    private String rol;
    private Short edad;
    private Short estado;

    public Usuario() {}
    public Usuario(String nombres, String apellidos, String numeroDocumento, String rol, short edad, Short estado) {
        super(nombres, apellidos, numeroDocumento);
        this.rol = rol;
        this.edad = edad;
        this.estado = estado;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
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

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }
}
