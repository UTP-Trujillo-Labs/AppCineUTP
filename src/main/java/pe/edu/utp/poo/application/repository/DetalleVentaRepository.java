package pe.edu.utp.poo.application.repository;

import pe.edu.utp.poo.application.db.Conexion;
import pe.edu.utp.poo.application.model.DetalleVenta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleVentaRepository implements Repository<DetalleVenta> {
    @Override
    public Integer insert(DetalleVenta detalleVenta) throws SQLException {
        String query = """
                insert into Detalle_Venta (
                       venta_id,
                       tipo_tickets,
                       precio_unitario,
                       cantidad_tickets,
                       descripcion
                 )
                 VALUES (?, ?, ?, ?, ?)
                """;
        try (
                Connection conn = Conexion.getInstance();
                PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            ps.setInt(1, detalleVenta.getVentaId());
            ps.setString(2, detalleVenta.getTipoTickets());
            ps.setDouble(3, detalleVenta.getPrecio());
            ps.setInt(4, detalleVenta.getCantidad());
            ps.setString(5, detalleVenta.getDescripcion());

            int result = ps.executeUpdate();
            if (result == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                return rs.getInt(1);
            }
        }
        return null;
    }

    @Override
    public boolean update(DetalleVenta detalleButaca) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Depredecate, en su lugar utilizar: listByVentaId(Integer ventaId)
     * @return
     * @throws SQLException
     */
    @Override
    @Deprecated
    public List<DetalleVenta> list() throws SQLException{
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<DetalleVenta> listByVentaId(Integer ventaId) throws SQLException{
        List<DetalleVenta> list = new ArrayList<>();
        String query = """
                select venta_id, tipo_tickets, precio_unitario, cantidad_tickets, descripcion
                from Detalle_Venta
                where venta_id = ?
                """;
        try (
                Connection conn = Conexion.getInstance();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setInt(1, ventaId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DetalleVenta detalleVenta = new DetalleVenta();
                detalleVenta.setVentaId(rs.getInt("venta_id"));
                detalleVenta.setTipoTickets(rs.getString("tipo_tickets"));
                detalleVenta.setDetalleVentaId(rs.getInt("precio_unitario"));
                detalleVenta.setCantidad(rs.getInt("cantidad_tickets"));
                detalleVenta.setDescripcion(rs.getString("descripcion"));

                list.add(detalleVenta);
            }
        }
        return list;
    }

    @Override
    public DetalleVenta findById(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
