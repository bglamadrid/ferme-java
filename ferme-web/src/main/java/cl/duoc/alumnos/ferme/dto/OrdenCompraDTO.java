package cl.duoc.alumnos.ferme.dto;

import java.util.List;

/**
 *
 * @author Benjamin Guillermo
 */
public class OrdenCompraDTO {
    
    private Integer idOrdenCompra;
    private Integer idEmpleado;
    private String estadoOrdenCompra;
    private String fechaSolicitudOrdenCompra;
    private String fechaRecepcionOrdenCompra;
    private List<DetalleOrdenCompraDTO> detallesOrdenCompra;

    public OrdenCompraDTO() {
        super();
    }
    
    public Integer getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(Integer idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getEstadoOrdenCompra() {
        return estadoOrdenCompra;
    }

    public void setEstadoOrdenCompra(String estadoOrdenCompra) {
        this.estadoOrdenCompra = estadoOrdenCompra;
    }

    public String getFechaSolicitudOrdenCompra() {
        return fechaSolicitudOrdenCompra;
    }

    public void setFechaSolicitudOrdenCompra(String fechaSolicitudOrdenCompra) {
        this.fechaSolicitudOrdenCompra = fechaSolicitudOrdenCompra;
    }

    public String getFechaRecepcionOrdenCompra() {
        return fechaRecepcionOrdenCompra;
    }

    public void setFechaRecepcionOrdenCompra(String fechaRecepcionOrdenCompra) {
        this.fechaRecepcionOrdenCompra = fechaRecepcionOrdenCompra;
    }

    public List<DetalleOrdenCompraDTO> getDetallesOrdenCompra() {
        return detallesOrdenCompra;
    }

    public void setDetallesOrdenCompra(List<DetalleOrdenCompraDTO> detallesOrdenCompra) {
        this.detallesOrdenCompra = detallesOrdenCompra;
    }
    
}
