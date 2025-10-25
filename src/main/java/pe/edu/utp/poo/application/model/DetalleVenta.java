package pe.edu.utp.poo.application.model;

public class DetalleVenta {
    private Integer detalleVentaId;
    private Integer ventaId;
    private String tipoTickets;
    private Double precio;
    private Integer cantidad;
    private String descripcion;

    public DetalleVenta  () {}

    public DetalleVenta(Integer detalleVentaId, Integer ventaId, String tipoTickets, Double precio, Integer cantidad, String descripcion) {
        this.detalleVentaId = detalleVentaId;
        this.ventaId = ventaId;
        this.tipoTickets = tipoTickets;
        this.precio = precio;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
    }

    public int getDetalleVentaId() {
        return detalleVentaId;
    }

    public void setDetalleVentaId(int detalleVentaId) {
        this.detalleVentaId = detalleVentaId;
    }

    public int getVentaId() {
        return ventaId;
    }

    public void setVentaId(int ventaId) {
        this.ventaId = ventaId;
    }

    public String getTipoTickets() {
        return tipoTickets;
    }

    public void setTipoTickets(String tipoTickets) {
        this.tipoTickets = tipoTickets;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
