/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import pe.edu.utp.poo.application.db.Conexion;
import pe.edu.utp.poo.application.model.Cliente;

/**
 *
 * @author axcel
 */
public class ClienteRepository implements Repository<Cliente> {

    @Override
    public Long insert(Cliente cliente) throws SQLException {
        
      String query="""
                   INSERT INTO Cliente(nombres, apellidos, numero_documento)
                    VALUES(?,?,?)
                   """;
      try(Connection conn = Conexion.getInstance();
          PreparedStatement insertarClien = conn.prepareStatement(query)){
          insertarClien.setString(1, cliente.getNombres());
          insertarClien.setString(2, cliente.getApellidos());
          insertarClien.setString(3, cliente.getNumeroDocumento());
          insertarClien.executeUpdate();
      }catch(SQLException e){
          e.printStackTrace();
      }
      return null;
            
    }
    

    @Override
    public boolean update(Cliente t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Cliente> list() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Cliente findById(Long id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(Long id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
