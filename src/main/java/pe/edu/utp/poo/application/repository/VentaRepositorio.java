/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.repository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import pe.edu.utp.poo.application.db.DBConnection;
import pe.edu.utp.poo.application.model.Venta;

/**
 *
 * @author manuelguarniz
 */
public class VentaRepositorio extends CRUDRepository<Venta>{
    private static VentaRepositorio instance;
    public VentaRepositorio() {
    }
    public static VentaRepositorio getInstance() {
        if (instance == null) {
            instance = new VentaRepositorio();
        }
        return instance;
    }
    public List<Venta> findAll() throws SQLException {
        
        List<Map<String, Object>> data = DBConnection.getInstance()
                .connect()
                .query("select fecha_venta, pelicula, horario, total_asientos, precio_total from Venta")
//                .query("select fecha_venta, pelicula, horario, total_asientos, precio_total "
//                        + "from Venta "
//                        + "where total_asientos = ? and horario = ?")
//                .params(3, "7pm")
                .get();
        List<Venta> lista = this.binding(data, Venta.class);
        return lista;
    }
    
    
    
    public Venta save(Venta venta) throws SQLException {
        int result = DBConnection.getInstance()
                .connect()
                .query("insert into Venta (fecha_venta, pelicula, horario, total_asientos, precio_total) " +
                        "values (?, ?, ?, ?, ?)")
                .params(venta.getFechaVenta(),
                        venta.getPelicula(),
                        venta.getHorario(),
                        venta.getTotalAsientos(),
                        venta.getPrecioTotal())
                .save();
                
        if (result <= 0) {
            throw new RuntimeException("No se guardó");
        }
        return venta;
    }
}
