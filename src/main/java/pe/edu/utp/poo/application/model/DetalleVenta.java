package pe.edu.utp.poo.application.model;

public class DetalleVenta {
    private int detalleVentaId;
    private int ventaId;
    private String tipoTickets;
    private int cantidadTickets;
    private String asiento;

    public DetalleVenta  () {}

    public DetalleVenta(int detalleVentaId, int ventaId, String tipoTickets, int cantidadTickets, String asiento) {
        this.detalleVentaId = detalleVentaId;
        this.ventaId = ventaId;
        this.tipoTickets = tipoTickets;
        this.cantidadTickets = cantidadTickets;
        this.asiento = asiento;
    }

    public int getDetalleVentaId() {
        return detalleVentaId;
    }

    public void setDetalleVentaId(int detalleVentaId) {
        this.detalleVentaId = detalleVentaId;
    }

    public int getVentaId() {
        return ventaId;
    }

    public void setVentaId(int ventaId) {
        this.ventaId = ventaId;
    }

    public String getTipoTickets() {
        return tipoTickets;
    }

    public void setTipoTickets(String tipoTickets) {
        this.tipoTickets = tipoTickets;
    }

    public int getCantidadTickets() {
        return cantidadTickets;
    }

    public void setCantidadTickets(int cantidadTickets) {
        this.cantidadTickets = cantidadTickets;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }
}
