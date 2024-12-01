/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.repository;

import java.util.List;

public interface Repository<T> {
    T save(T t);
    List<T> list();
    T findById(Long id);
    boolean delete(Long id);
}
