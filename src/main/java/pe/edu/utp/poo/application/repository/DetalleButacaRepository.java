package pe.edu.utp.poo.application.repository;

import pe.edu.utp.poo.application.db.Conexion;
import pe.edu.utp.poo.application.model.DetalleButaca;
import pe.edu.utp.poo.application.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleButacaRepository implements Repository<DetalleButaca> {
    @Override
    public Integer insert(DetalleButaca detalleButaca) throws SQLException {
        String query = """
                insert into Detalle_Butacas (
                     venta_id,
                     detalle_venta_id,
                     numero_butaca
                 )
                 VALUES (?, ?, ?)
                """;
        try (
                Connection conn = Conexion.getInstance();
                PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            ps.setInt(1, detalleButaca.getVentaId());
            ps.setInt(2, detalleButaca.getDetalleVentaId());
            ps.setString(3, detalleButaca.getNumeroButaca());

            Integer result = ps.executeUpdate();
            if (result == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                return rs.getInt(1);
            }
        }
        return null;
    }

    @Override
    public boolean update(DetalleButaca detalleButaca) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Depredecate, en su lugar utilizar: listByVentaId(Integer ventaId)
     * @return
     * @throws SQLException
     */
    @Override
    @Deprecated
    public List<DetalleButaca> list() throws SQLException{
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<DetalleButaca> listByVentaId(Integer ventaId) throws SQLException{
        List<DetalleButaca> list = new ArrayList<>();
        String query = """
                select detalle_butacas_id, venta_id, detalle_venta_id, numero_butaca
                from Detalle_Butacas
                where venta_id = ?
                """;
        try (
                Connection conn = Conexion.getInstance();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setInt(1, ventaId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DetalleButaca butaca = new DetalleButaca();
                butaca.setDetalleButacaId(rs.getInt("detalle_butacas_id"));
                butaca.setVentaId(rs.getInt("venta_id"));
                butaca.setDetalleVentaId(rs.getInt("detalle_venta_id"));
                butaca.setNumeroButaca(rs.getString("numero_butaca"));

                list.add(butaca);
            }
        }
        return list;
    }

    public List<String> listByPeliculaId(Integer peliculaId) throws SQLException{
        List<String> list = new ArrayList<>();
        String query = """
                select t3.numero_butaca from dbo.Venta t1
                  inner join dbo.Pelicula t2 on t1.pelicula_id = t2.pelicula_id
                  inner join dbo."Detalle_Butacas" t3 on t1.venta_id = t3.venta_id
                  where t1.pelicula_id = ?
                """;
        try (
                Connection conn = Conexion.getInstance();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setInt(1, peliculaId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String numeroButaca = rs.getString("numero_butaca");
                list.add(numeroButaca);
            }
        }
        return list;
    }

    @Override
    public DetalleButaca findById(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        String query = """
                delete from Detalle_Butacas
                where detalle_butacas_id = ?
                """;
        try (
                Connection conn = Conexion.getInstance();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setInt(1, id);

            int result = ps.executeUpdate();
            if (result == 1) {
                return true;
            }
        }
        return false;
    }
}
