package cl.duoc.alumnos.ferme.dto;

/**
 *
 * @author Benjamin Guillermo
 */
public class DetalleVentaDTO {
    
    private Integer idDetalleVenta;
    private Integer idVenta;
    private Integer idProducto;
    private Integer unidades;
    private Integer montoDetalle;

    public DetalleVentaDTO() {}

    public Integer getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(Integer idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getUnidades() {
        return unidades;
    }

    public void setUnidades(Integer unidades) {
        this.unidades = unidades;
    }

    public Integer getMontoDetalle() {
        return montoDetalle;
    }

    public void setMontoDetalle(Integer montoDetalle) {
        this.montoDetalle = montoDetalle;
    }
    
}
