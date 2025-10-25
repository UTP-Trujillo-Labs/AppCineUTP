package pe.edu.utp.poo.application.model;

public class Seguridad {
    private Integer seguridadId;
    private Usuario usuario;
    private String usuarioLogin;
    private String clave;
    private Short activo;

    public Seguridad() { }

    public Seguridad(Integer seguridadId, Usuario usuario, String usuarioLogin, String clave, Short activo) {
        this.seguridadId = seguridadId;
        this.usuario = usuario;
        this.usuarioLogin = usuarioLogin;
        this.clave = clave;
        this.activo = activo;
    }

    public Integer getSeguridadId() {
        return seguridadId;
    }

    public void setSeguridadId(Integer seguridadId) {
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

    public static class DetalleVenta {

        private Integer detalleVentaId;
        private Integer ventaId;
        private String tipoTickets;
        private Double precio;
        private Integer cantidad;
        private String descripcion;

        public DetalleVenta() {
        }

        public DetalleVenta(Integer detalleVentaId, Integer ventaId, String tipoTickets, Double precio, Integer cantidad, String descripcion) {
            this.detalleVentaId = detalleVentaId;
            this.ventaId = ventaId;
            this.tipoTickets = tipoTickets;
            this.precio = precio;
            this.cantidad = cantidad;
            this.descripcion = descripcion;
        }

        public Integer getDetalleVentaId() {
            return detalleVentaId;
        }

        public void setDetalleVentaId(Integer detalleVentaId) {
            this.detalleVentaId = detalleVentaId;
        }

        public Integer getVentaId() {
            return ventaId;
        }

        public void setVentaId(Integer ventaId) {
            this.ventaId = ventaId;
        }

        public String getTipoTickets() {
            return tipoTickets;
        }

        public void setTipoTickets(String tipoTickets) {
            this.tipoTickets = tipoTickets;
        }

        public Double getPrecio() {
            return precio;
        }

        public void setPrecio(Double precio) {
            this.precio = precio;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }
    }
}
