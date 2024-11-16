/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.poo.application.model;

import java.util.ArrayList;
import java.util.List;
import static pe.edu.utp.poo.application.common.Constant.DEFAULT_ELEMENTOS_POR_PAGINA;

/**
 *
 * @author manuelguarniz
 * @param <T> Modelo de base de datos
 */

public class Paginacion<T> {

    private Integer totalElementos = 0;
    private Integer offset = 0;
    private Integer maxElementoPorPagina = DEFAULT_ELEMENTOS_POR_PAGINA;
    private List<T> elementos = new ArrayList<>();

    public Paginacion() { }
    public Paginacion(Integer totalElementos, Integer offset, Integer maxElementoPorPagina, List<T> elementos) {
        this.totalElementos = totalElementos;
        this.offset = offset;
        this.maxElementoPorPagina = maxElementoPorPagina;
        this.elementos = elementos;
    }

    public Integer getTotalElementos() {
        return totalElementos;
    }

    public void setTotalElementos(Integer totalElementos) {
        this.totalElementos = totalElementos;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public List<T> getElementos() {
        return elementos;
    }

    public void setElementos(List<T> elementos) {
        this.elementos = elementos;
    }

    public Integer getMaxElementoPorPagina() {
        return maxElementoPorPagina;
    }

    public void setMaxElementoPorPagina(Integer maxElementoPorPagina) {
        this.maxElementoPorPagina = maxElementoPorPagina;
    }

    public int totalPaginas() {
        if (maxElementoPorPagina == 0 || totalElementos == 0) {
            return 0;
        }
        return (int) Math.ceil(this.totalElementos / (double) this.maxElementoPorPagina);
    }

    public int paginaActual() {
        if (this.offset == 0 || this.maxElementoPorPagina == 0 || this.offset < this.maxElementoPorPagina) {
            return 1;
        }
        return (this.offset / this.maxElementoPorPagina) + 1;
    }
}
