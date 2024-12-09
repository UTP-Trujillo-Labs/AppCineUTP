package pe.edu.utp.poo.application.model;

import java.time.LocalDateTime;

public class ReporteVenta {
    private Integer ventaId;
    private String nombreCliente;
    private String nombreUsuario;
    private String tituloPelicula;
    private String horario;
    private LocalDateTime fechaVenta;
    private Integer cantidadTickets;
    private Double subtotal;

    public ReporteVenta() {
    }

    public ReporteVenta(Integer ventaId, String nombreCliente, String nombreUsuario, String tituloPelicula, String horario, LocalDateTime fechaVenta, Integer cantidadTickets, Double subtotal) {
        this.ventaId = ventaId;
        this.nombreCliente = nombreCliente;
        this.nombreUsuario = nombreUsuario;
        this.tituloPelicula = tituloPelicula;
        this.horario = horario;
        this.fechaVenta = fechaVenta;
        this.cantidadTickets = cantidadTickets;
        this.subtotal = subtotal;
    }

    public Integer getVentaId() {
        return ventaId;
    }

    public void setVentaId(Integer ventaId) {
        this.ventaId = ventaId;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getTituloPelicula() {
        return tituloPelicula;
    }

    public void setTituloPelicula(String tituloPelicula) {
        this.tituloPelicula = tituloPelicula;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Integer getCantidadTickets() {
        return cantidadTickets;
    }

    public void setCantidadTickets(Integer cantidadTickets) {
        this.cantidadTickets = cantidadTickets;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}
