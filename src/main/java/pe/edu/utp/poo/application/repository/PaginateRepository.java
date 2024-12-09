package pe.edu.utp.poo.application.repository;

import pe.edu.utp.poo.application.model.Paginacion;

import java.sql.SQLException;
import java.util.List;

public interface PaginateRepository <T> {
    Paginacion<T> listaPaginada(Integer offset, Integer limite) throws SQLException;
    Integer totalElementos() throws SQLException;
}
