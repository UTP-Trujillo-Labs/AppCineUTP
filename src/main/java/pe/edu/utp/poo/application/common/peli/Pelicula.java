/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.common.peli;

/**
 *
 * @author USUARIO
 */
public class Pelicula {
    
    
    private String titulo;
    private String autor;
    private String duracion;
    private String genero;
    private boolean menores;

    public Pelicula(String titulo, String autor, String duracion, String genero, boolean menores) {
        this.titulo = titulo;
        this.autor = autor;
        this.duracion = duracion;
        this.genero = genero;
        this.menores = menores;
    }
    

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public boolean isMenores() {
        return menores;
    }

    public void setMenores(boolean menores) {
        this.menores = menores;
    }
}
