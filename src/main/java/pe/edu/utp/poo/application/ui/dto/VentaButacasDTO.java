package pe.edu.utp.poo.application.ui.dto;

import java.util.List;
import java.util.function.Consumer;

public class VentaButacasDTO {
    private Long idDetalleVentaButaca;
    private String pelicula;
    private String horario;
    private Integer cantidadAsientosAdulto;
    private Integer cantidadAsientosNinio;

    private List<String> butacasReservadas;

    public VentaButacasDTO() {
    }

    public VentaButacasDTO(String pelicula, String horario, Integer cantidadAsientosAdulto, Integer cantidadAsientosNinio) {
        this.pelicula = pelicula;
        this.horario = horario;
        this.cantidadAsientosAdulto = cantidadAsientosAdulto;
        this.cantidadAsientosNinio = cantidadAsientosNinio;
    }

    public String getPelicula() {
        return pelicula;
    }

    public void setPelicula(String pelicula) {
        this.pelicula = pelicula;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Integer getCantidadAsientosAdulto() {
        return cantidadAsientosAdulto;
    }

    public void setCantidadAsientosAdulto(Integer cantidadAsientosAdulto) {
        this.cantidadAsientosAdulto = cantidadAsientosAdulto;
    }

    public Integer getCantidadAsientosNinio() {
        return cantidadAsientosNinio;
    }

    public void setCantidadAsientosNinio(Integer cantidadAsientosNinio) {
        this.cantidadAsientosNinio = cantidadAsientosNinio;
    }

    public List<String> getButacasReservadas() {
        return butacasReservadas;
    }

    public void setButacasReservadas(List<String> butacasReservadas) {
        this.butacasReservadas = butacasReservadas;
    }

    @Override
    public String toString() {
        return "VentaButacasDTO{" +
                "idDetalleVentaButaca=" + idDetalleVentaButaca +
                ", pelicula='" + pelicula + '\'' +
                ", horario='" + horario + '\'' +
                ", cantidadAsientosAdulto=" + cantidadAsientosAdulto +
                ", cantidadAsientosNinio=" + cantidadAsientosNinio +
                ", butacasReservadas=" + butacasReservadas +
                '}';
    }
}
