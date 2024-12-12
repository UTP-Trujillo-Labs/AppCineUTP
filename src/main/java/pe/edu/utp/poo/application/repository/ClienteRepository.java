/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.repository;

import java.sql.*;
import java.util.List;
import pe.edu.utp.poo.application.db.Conexion;
import pe.edu.utp.poo.application.model.Cliente;

/**
 *
 * @author axcel
 */
public class ClienteRepository implements Repository<Cliente> {

    @Override
    public Integer insert(Cliente cliente) throws SQLException {
        
      String query="""
                   INSERT INTO Cliente(nombres, apellidos, numero_documento)
                    VALUES(?,?,?)
                   """;
      try(Connection conn = Conexion.getInstance();
          PreparedStatement insertarClien = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
          insertarClien.setString(1, cliente.getNombres());
          insertarClien.setString(2, cliente.getApellidos());
          insertarClien.setString(3, cliente.getNumeroDocumento());

          Integer result = insertarClien.executeUpdate();
          if (result == 1) {
              ResultSet rs = insertarClien.getGeneratedKeys();
              rs.next();
              return rs.getInt(1);
          }
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
    public Cliente findById(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
