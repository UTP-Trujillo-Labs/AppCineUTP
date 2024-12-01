/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import pe.edu.utp.poo.application.db.DBConnection;
import pe.edu.utp.poo.application.model.Paginacion;
import pe.edu.utp.poo.application.model.VentasDemo;

/**
 *
 * @author manuelguarniz
 */
public class VentaRepositorio extends CRUDRepository<VentasDemo>{
    private static VentaRepositorio instance;
    public VentaRepositorio() {
    }
    public static VentaRepositorio getInstance() {
        if (instance == null) {
            instance = new VentaRepositorio();
        }
        return instance;
    }
    public List<VentasDemo> findAll() throws SQLException {
        
        List<Map<String, Object>> data = DBConnection.getInstance()
                .connect()
                .query("select fecha_venta, pelicula, horario, total_asientos, precio_total from Venta")
//                .query("select fecha_venta, pelicula, horario, total_asientos, precio_total "
//                        + "from Venta "
//                        + "where total_asientos = ? and horario = ?")
//                .params(3, "7pm")
                .get();
        List<VentasDemo> lista = this.binding(data, VentasDemo.class);
        return lista;
    }
    
    public Paginacion<VentasDemo> paginacion(int offset, int numeroElementos) throws SQLException {
        DBConnection connection = DBConnection.getInstance()
                .connect();
        Long refTotalRows = 0l;
        
        Map<String, Object> metadata = connection.query("select count(*) totalFilas from Venta").single();
        
        Integer totalFilas = metadata.containsKey("totalFilas") ? (int) metadata.get("totalFilas") : 0;
        
        List<Map<String, Object>> data = connection
                .query("select fecha_venta, pelicula, horario, total_asientos, precio_total " +
                        "from Venta " +
                        "order by fecha_venta desc " +
                        "offset ? rows " +
                        "fetch next ? rows only")
                .params(offset, numeroElementos)
                .get();
        List<VentasDemo> lista = this.binding(data, VentasDemo.class);
        return new Paginacion<>(totalFilas, offset, numeroElementos, lista);
    }
    
    public VentasDemo save(VentasDemo ventasDemo) throws SQLException {
        int result = DBConnection.getInstance()
                .connect()
                .query("insert into Venta (fecha_venta, pelicula, horario, total_asientos, precio_total) " +
                        "values (?, ?, ?, ?, ?)")
                .params(ventasDemo.getFechaVenta(),
                        ventasDemo.getPelicula(),
                        ventasDemo.getHorario(),
                        ventasDemo.getTotalAsientos(),
                        ventasDemo.getPrecioTotal())
                .save();
                
        if (result <= 0) {
            throw new RuntimeException("No se guardó");
        }
        return ventasDemo;
    }
}
