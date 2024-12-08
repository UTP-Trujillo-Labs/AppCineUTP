package pe.edu.utp.poo.application.repository;

import pe.edu.utp.poo.application.common.Util;
import pe.edu.utp.poo.application.db.Conexion;
import pe.edu.utp.poo.application.model.Paginacion;
import pe.edu.utp.poo.application.model.Venta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VentaRepository implements PaginateRepository<Venta> {

    @Override
    public Paginacion<Venta> listaPaginada(Integer offset, Integer limite) throws SQLException {
        List<Venta> list = new ArrayList<>();
        Integer totalElementos = this.totalElementos();
        String query = """
                select venta_id, cliente_id, usuario_id, pelicula_id, horario, convert(varchar(25), fecha_venta, 120) fecha_venta
                from Venta
                order by fecha_venta desc
                offset
                    ? rows
                fetch next
                    ? rows only
                """;
        try (
                Connection conn = Conexion.getInstance();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setLong(1, offset);
            ps.setLong(2, limite);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Venta venta = new Venta();
                venta.setVentaId(rs.getInt("venta_id"));
                venta.setClienteId(rs.getInt("cliente_id"));
                venta.setUsuarioId(rs.getInt("usuario_id"));
                venta.setPeliculaId(rs.getInt("pelicula_id"));
                venta.setHorario(rs.getString("horario"));
                venta.setFechaVenta(Util.parseDatetime(rs.getString("fecha_venta")));

                list.add(venta);
            }
        }
        return new Paginacion<>(totalElementos, offset, limite, list);
    }

    @Override
    public Integer totalElementos() throws SQLException {
        String query = """
                select count(1) totalFilas from Venta
                """;

        try (
                Connection conn = Conexion.getInstance();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("totalFilas");
        }
    }
}
