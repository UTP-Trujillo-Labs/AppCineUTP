/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.common.peli;

import java.util.ArrayList;

/**
 *
 * @author USUARIO
 */
public class GestorPeliculas {
    ArrayList<Pelicula>peliculas;

    public GestorPeliculas() {
        peliculas=new ArrayList<>();
    }

    
    public void agregarPelicula(Pelicula pelicula){
        peliculas.add(pelicula);
    }
    
    public void eliminarPelicula(int indice){
        if(indice>=0 && indice<peliculas.size()){
        
            peliculas.remove(indice);
        }
    }

    public void actualizarPelicula(int indice,Pelicula peliculaActualizada){
        if(indice>=0 && indice<peliculas.size()){
        
            peliculas.set(indice, peliculaActualizada);
        }
    }
    
    public ArrayList<Pelicula>mostrarPeliculas(){
        return peliculas;
}
    
}
