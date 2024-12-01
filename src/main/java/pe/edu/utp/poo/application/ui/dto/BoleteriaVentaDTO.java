package pe.edu.utp.poo.application.ui.dto;

public class BoleteriaVentaDTO {
    private String descripcion;
    private Double precio;
    private Integer cantidad;
    private Double subtotal;

    public BoleteriaVentaDTO() {
    }

    public BoleteriaVentaDTO(String descripcion, Double precio, Integer cantidad, Double subtotal) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}
