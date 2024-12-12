/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.repository;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {
    Integer insert(T t) throws SQLException;
    boolean update(T t) throws SQLException;
    List<T> list() throws SQLException;
    T findById(Integer id) throws SQLException;
    boolean delete(Integer id) throws SQLException;
}
