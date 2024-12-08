package pe.edu.utp.poo.application.model;

public class DetalleButaca {
    private Integer detalleButacaId;
    private Integer ventaId;
    private Integer detalleVentaId;
    private String numeroButaca;

    public DetalleButaca() {
    }

    public DetalleButaca(Integer detalleButacaId, Integer ventaId, Integer detalleVentaId, String numeroButaca) {
        this.detalleButacaId = detalleButacaId;
        this.ventaId = ventaId;
        this.detalleVentaId = detalleVentaId;
        this.numeroButaca = numeroButaca;
    }

    public Integer getDetalleButacaId() {
        return detalleButacaId;
    }

    public void setDetalleButacaId(Integer detalleButacaId) {
        this.detalleButacaId = detalleButacaId;
    }

    public Integer getVentaId() {
        return ventaId;
    }

    public void setVentaId(Integer ventaId) {
        this.ventaId = ventaId;
    }

    public Integer getDetalleVentaId() {
        return detalleVentaId;
    }

    public void setDetalleVentaId(Integer detalleVentaId) {
        this.detalleVentaId = detalleVentaId;
    }

    public String getNumeroButaca() {
        return numeroButaca;
    }

    public void setNumeroButaca(String numeroButaca) {
        this.numeroButaca = numeroButaca;
    }
}
