/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.model;

import pe.edu.utp.poo.application.common.Util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author manuelguarniz
 */
public class Venta {
    private int ventaId;
    private int clienteId;
    private int usuarioId;
    private int peliculaId;
    private String horario;
    private LocalDateTime fechaVenta;


    public Venta() {
    }

    public Venta(int ventaId, int clienteId, int usuarioId, int peliculaId, String horario, LocalDateTime fechaVenta) {
        this.ventaId = ventaId;
        this.clienteId = clienteId;
        this.usuarioId = usuarioId;
        this.peliculaId = peliculaId;
        this.horario = horario;
        this.fechaVenta = fechaVenta;
    }

    public int getVentaId() {
        return ventaId;
    }

    public void setVentaId(int ventaId) {
        this.ventaId = ventaId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getPeliculaId() {
        return peliculaId;
    }

    public void setPeliculaId(int peliculaId) {
        this.peliculaId = peliculaId;
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
    public String getFechaVentaString() {
        return Util.dateToString(fechaVenta);
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }
}
