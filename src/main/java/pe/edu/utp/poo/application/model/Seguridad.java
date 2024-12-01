package pe.edu.utp.poo.application.model;

public class Seguridad {
    private Long seguridadId;
    private Usuario usuario;
    private String usuarioLogin;
    private String clave;
    private boolean activo;

    public Seguridad() { }

    public Seguridad(Long seguridadId, Usuario usuario, String usuarioLogin, String clave, boolean activo) {
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
