/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.model;

import java.time.LocalDateTime;
import static pe.edu.utp.poo.application.common.Constant.SHORT_LOCALDATETIME_FORMAT;
import pe.edu.utp.poo.application.common.Util;
import pe.edu.utp.poo.application.db.annotation.ColumnName;

/**
 *
 * @author manuelguarniz
 */
public class Venta {
    @ColumnName("fecha_venta")
    private LocalDateTime fechaVenta;
    @ColumnName("pelicula")
    private String pelicula;
    @ColumnName("horario")
    private String horario;
    @ColumnName("total_asientos")
    private Integer totalAsientos;
    @ColumnName("precio_total")
    private Double precioTotal;

    public Venta() {}
    public Venta(LocalDateTime fechaVenta, String pelicula, String horario, Integer totalAsientos, Double precioTotal) {
        this.fechaVenta = fechaVenta;
        this.pelicula = pelicula;
        this.horario = horario;
        this.totalAsientos = totalAsientos;
        this.precioTotal = precioTotal;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }
    
    public String getFechaVentaStr() {
        return Util.dateToString(fechaVenta, SHORT_LOCALDATETIME_FORMAT);
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getPelicula() {
        return pelicula;
    }
    
    public void setPelicula(String pelicula) {
        this.pelicula = pelicula;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Integer getTotalAsientos() {
        return totalAsientos;
    }

    public void setTotalAsientos(Integer totalAsientos) {
        this.totalAsientos = totalAsientos;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    @Override
    public String toString() {
        return "Venta{" + "fechaVenta=" + fechaVenta + ", pelicula=" + pelicula + ", horario=" + horario + ", totalAsientos=" + totalAsientos + ", precioTotal=" + precioTotal + '}';
    }
    
    
}
