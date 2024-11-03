/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.lib;

import java.util.List;

/**
 *
 * @author manuelguarniz
 * @param <T>
 */
public interface ICRUDLogica<T> {
    List<T> listar();
    T obtenerPorId(String id);
    T guardar(T data) throws RuntimeException;
    boolean eliminar(String id);
}
