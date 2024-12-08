package pe.edu.utp.poo.application.repository;

import pe.edu.utp.poo.application.common.Util;
import pe.edu.utp.poo.application.db.Conexion;
import pe.edu.utp.poo.application.model.Paginacion;
import pe.edu.utp.poo.application.model.ReporteVenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReporteRepository implements PaginateRepository<ReporteVenta> {

    @Override
    public Paginacion<ReporteVenta> listaPaginada(Integer offset, Integer limite) throws SQLException {
        List<ReporteVenta> list = new ArrayList<>();
        Integer totalElementos = this.totalElementos();
        String query = """
                select
                    t1.venta_id,
                    t3.nombres + ' ' + t3.apellidos nombre_cliente,
                    t4.nombres + ' ' + t4.apellidos nombre_usuario,
                    t2.titulo titulo_pelicula,
                    t1.horario,
                    convert(
                        varchar(25),
                        t1.fecha_venta,
                        120
                    ) fecha_venta,
                    t5.precio_unitario,
                    t5.cantidad_tickets,
                    sum(
                        t5.precio_unitario * t5.cantidad_tickets
                    ) subtotal
                from
                    Venta t1
                    inner join Pelicula t2 on t1.pelicula_id = t2.pelicula_id
                    inner join Cliente t3 on t1.cliente_id = t3.cliente_id
                    inner join Usuario t4 on t1.usuario_id = t4.usuario_id
                    inner join Detalle_Venta t5 on t1.venta_id = t5.venta_id
                group by
                    t1.venta_id,
                    t3.nombres,
                    t3.apellidos,
                    t4.nombres,
                    t4.apellidos,
                    t2.titulo,
                    t1.horario,
                    t1.fecha_venta,
                    t5.precio_unitario,
                    t5.cantidad_tickets
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
                ReporteVenta reporteVenta = new ReporteVenta();
                reporteVenta.setVentaId(rs.getInt("venta_id"));
                reporteVenta.setNombreCliente(rs.getString("nombre_cliente"));
                reporteVenta.setNombreUsuario(rs.getString("nombre_usuario"));
                reporteVenta.setTituloPelicula(rs.getString("titulo_pelicula"));
                reporteVenta.setHorario(rs.getString("horario"));
                reporteVenta.setFechaVenta(Util.parseDatetime(rs.getString("fecha_venta")));
                reporteVenta.setPrecioUnitario(rs.getDouble("precio_unitario"));
                reporteVenta.setCantidadTickets(rs.getInt("cantidad_tickets"));
                reporteVenta.setSubtotal(rs.getDouble("subtotal"));

                list.add(reporteVenta);
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
