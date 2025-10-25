/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.service;

import java.sql.SQLException;
import java.util.List;
import pe.edu.utp.poo.application.model.Pelicula;
import pe.edu.utp.poo.application.model.Usuario;
import pe.edu.utp.poo.application.repository.PeliculaRepository;

/**
 *
 * @author USUARIO
 */
public class PeliculaService {
    
    private PeliculaRepository peliculaRepository;

    public PeliculaService() {
       peliculaRepository = new PeliculaRepository();
    }
    
    
     public List<Pelicula> findAll() throws SQLException {
        return this.peliculaRepository.list();
    }
    
     public Integer insertar(Pelicula pelicula) throws SQLException {
        return peliculaRepository.insert(pelicula);
    } 
     
    
    public boolean actualizar(Pelicula pelicula)throws SQLException{
        return peliculaRepository.update(pelicula);
    }
    
    public boolean eliminar(Integer pelicula_id) throws SQLException {
    return peliculaRepository.delete(pelicula_id);
}
    
    
}
