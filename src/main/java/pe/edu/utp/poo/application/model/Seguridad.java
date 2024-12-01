package pe.edu.utp.poo.application.model;

public class Seguridad {
    private Long seguridadId;
    private Usuario usuario;
    private String usuarioLogin;
    private String clave;
    private Short activo;

    public Seguridad() { }

    public Seguridad(Long seguridadId, Usuario usuario, String usuarioLogin, String clave, Short activo) {
        this.seguridadId = seguridadId;
        this.usuario = usuario;
        this.usuarioLogin = usuarioLogin;
        this.clave = clave;
        this.activo = activo;
    }

    public Long getSeguridadId() {
        return seguridadId;
    }

    public void setSeguridadId(Long seguridadId) {
        this.seguridadId = seguridadId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Short getActivo() {
        return activo;
    }

    public void setActivo(Short activo) {
        this.activo = activo;
    }
}
