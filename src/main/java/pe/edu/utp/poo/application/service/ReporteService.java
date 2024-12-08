package pe.edu.utp.poo.application.service;

import pe.edu.utp.poo.application.model.Paginacion;
import pe.edu.utp.poo.application.model.ReporteVenta;
import pe.edu.utp.poo.application.model.Venta;
import pe.edu.utp.poo.application.repository.ReporteRepository;

import java.sql.SQLException;

public class ReporteService {
    private ReporteRepository reporteRepository;

    public ReporteService() {
        this.reporteRepository = new ReporteRepository();
    }

    public Paginacion<ReporteVenta> listaPaginada(Integer offset, Integer limit) throws SQLException {
        return reporteRepository.listaPaginada(offset, limit);
    }
}
