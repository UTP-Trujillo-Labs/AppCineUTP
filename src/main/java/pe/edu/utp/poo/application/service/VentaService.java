package pe.edu.utp.poo.application.service;

import pe.edu.utp.poo.application.model.Paginacion;
import pe.edu.utp.poo.application.model.Venta;
import pe.edu.utp.poo.application.repository.VentaRepository;

import java.sql.SQLException;

public class VentaService {
    private VentaRepository ventaRepository;

    public VentaService() {
        this.ventaRepository = new VentaRepository();
    }

    public Paginacion<Venta> listaPaginada(Integer offset, Integer limit) throws SQLException {
        return ventaRepository.listaPaginada(offset, limit);
    }
}
