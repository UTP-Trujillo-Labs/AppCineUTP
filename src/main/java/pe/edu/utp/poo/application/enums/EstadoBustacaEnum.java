/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.enums;

import java.awt.Color;

/**
 *
 * @author manuelguarniz
 */
public enum EstadoBustacaEnum {
    Disponible("Disponible", new Color(255, 255, 255)),
    Ocupado("Ocupado", new Color(204, 204, 204)),
    Seleccion("Seleccion", new Color(153, 204, 255)),
    ;
    
    private final String valor;
    private final Color color;

    public String getValor() {
        return valor;
    }
    public Color getColor() {
        return color;
    }
        
    EstadoBustacaEnum(String valor, Color color) {
        this.valor = valor;
        this.color = color;
    }
    
    public static EstadoBustacaEnum parseEnum(String valor) {
        for (EstadoBustacaEnum e : values()) {
            if (e.getValor().equalsIgnoreCase(valor)) {
                return e;
            }
        }
        return EstadoBustacaEnum.Disponible;
    }
    
    public static EstadoBustacaEnum parseEnum(Color color) {
        for (EstadoBustacaEnum e : values()) {
            if (e.getColor().equals(color)) {
                return e;
            }
        }
        return EstadoBustacaEnum.Disponible;
    }
    
    public static EstadoBustacaEnum parseMixedEnum(String valor) {
        for (EstadoBustacaEnum e : values()) {
            if (e.getValor().contains(valor)) {
                return e;
            }
        }
        return EstadoBustacaEnum.Disponible;
    } 
    
}
