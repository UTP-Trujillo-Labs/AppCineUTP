/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.service;

import java.sql.SQLException;
import pe.edu.utp.poo.application.model.Cliente;
import pe.edu.utp.poo.application.repository.ClienteRepository;

/**
 *
 * @author axcel
 */
public class ClienteService {
    private ClienteRepository clienteRepository;
    
    public ClienteService() {
       clienteRepository = new ClienteRepository();
    }
    
    public Integer insertar(Cliente cliente) throws SQLException {
        return clienteRepository.insert(cliente);
    } 
     
}
