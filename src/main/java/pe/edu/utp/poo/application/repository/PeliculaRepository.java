/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pe.edu.utp.poo.application.db.Conexion;
import pe.edu.utp.poo.application.model.Pelicula;

/**
 *
 * @author USUARIO
 */
public class PeliculaRepository implements Repository<Pelicula> {

    @Override
    public Long insert(Pelicula pelicula) throws SQLException {
        
      String query="""
                   INSERT INTO Pelicula(titulo,autor,duracion,genero,apto_para_ninos)
                    VALUES(?,?,?,?,?)
                   """;
      try(Connection conn = Conexion.getInstance();
          PreparedStatement insertarPeli = conn.prepareStatement(query)    ){
          insertarPeli.setString(1, pelicula.getTitulo());
          insertarPeli.setString(2, pelicula.getAutor());
          insertarPeli.setShort(3, pelicula.getDuracion());
          insertarPeli.setString(4, pelicula.getGenero());
          insertarPeli.setBoolean(5,pelicula.isAptoParaNinos());
          insertarPeli.executeUpdate();
      }catch(SQLException e){
          e.printStackTrace();
      }
      return null;
            
    }
    
    

    @Override
    public boolean update(Pelicula pelicula) throws SQLException {
                 
        String sql = """
                     UPDATE Pelicula SET Titulo =? , Autor =? , Duracion = ?, Genero =? , apto_para_ninos =?
                                          WHERE pelicula_id = ? """;

        try (Connection conn = Conexion.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            
            pstmt.setString(1, pelicula.getTitulo());
            pstmt.setString(2, pelicula.getAutor());
            pstmt.setShort(3, pelicula.getDuracion());
            pstmt.setString(4, pelicula.getGenero());
            pstmt.setBoolean(5, pelicula.isAptoParaNinos());
            pstmt.setInt(6, pelicula.getPeliculaId());

            
            int rowsUpdated = pstmt.executeUpdate();

            
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }               
                     
    }

    
    @Override
    public List<Pelicula> list() throws SQLException {
        
       List<Pelicula> list=new ArrayList<>();
       String query="""
                    select pelicula_id, titulo
                               ,autor
                               ,duracion
                               ,genero
                               ,apto_para_ninos from Pelicula""";
       try(
               Connection conn = Conexion.getInstance();
                PreparedStatement ps = conn.prepareStatement(query);
               ){
       
              ResultSet rs = ps.executeQuery();
              while(rs.next()){
                  
                  Pelicula pelicula=new Pelicula();
                  pelicula.setPeliculaId(rs.getInt("pelicula_id"));
                  pelicula.setTitulo(rs.getString("titulo"));
                  pelicula.setAutor(rs.getString("autor"));
                  pelicula.setDuracion(rs.getShort("duracion"));
                  pelicula.setGenero(rs.getString("genero"));
                  pelicula.setAptoParaNinos(rs.getBoolean("apto_para_ninos"));
                                           
                  list.add(pelicula);
                  
              }
              return list;                  
       }catch(SQLException e){
           System.out.println("e"+e); 
           return null;
       }
              
    }

    @Override
    public Pelicula findById(Long id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
   
    

    @Override
    public boolean delete(Long id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
   
}
