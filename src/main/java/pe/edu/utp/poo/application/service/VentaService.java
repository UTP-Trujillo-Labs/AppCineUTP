package pe.edu.utp.poo.application.service;

import pe.edu.utp.poo.application.model.DetalleButaca;
import pe.edu.utp.poo.application.model.DetalleVenta;
import pe.edu.utp.poo.application.model.Paginacion;
import pe.edu.utp.poo.application.model.Venta;
import pe.edu.utp.poo.application.repository.DetalleButacaRepository;
import pe.edu.utp.poo.application.repository.DetalleVentaRepository;
import pe.edu.utp.poo.application.repository.VentaRepository;

import java.sql.SQLException;
import java.util.List;

public class VentaService {
    private VentaRepository ventaRepository;
    private DetalleVentaRepository detalleVentaRepository;
    private DetalleButacaRepository ventaButacaRepository;

    public VentaService() {
        this.ventaRepository = new VentaRepository();
        this.detalleVentaRepository = new DetalleVentaRepository();
        this.ventaButacaRepository = new DetalleButacaRepository();
    }

    public Paginacion<Venta> listaPaginada(Integer offset, Integer limit) throws SQLException {
        return ventaRepository.listaPaginada(offset, limit);
    }

    public Integer insertar(Venta venta) throws SQLException {
        return ventaRepository.insert(venta);
    }

    public Integer insertarDetalle(DetalleVenta detalleVenta) throws SQLException {
        return detalleVentaRepository.insert(detalleVenta);
    }
    public Integer insertarDetalleButacas(DetalleButaca detalleButaca) throws SQLException {
        return ventaButacaRepository.insert(detalleButaca);
    }

    public List<String> getButacasPorPeliculaId(Integer peliculaId) throws SQLException {
        return ventaButacaRepository.listByPeliculaId(peliculaId);
    }
}
