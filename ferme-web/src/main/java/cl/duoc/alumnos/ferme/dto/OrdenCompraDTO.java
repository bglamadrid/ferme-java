package cl.duoc.alumnos.ferme.dto;

import java.util.List;

/**
 *
 * @author Benjamin Guillermo
 */
public class OrdenCompraDTO {
    
    private Integer idOrdenCompra;
    private Integer idEmpleado;
    private String estado;
    private String fechaSolicitud;
    private String fechaRecepcion;
    private List<DetalleOrdenCompraDTO> detalles;

    public OrdenCompraDTO() {}
    
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(String fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public List<DetalleOrdenCompraDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleOrdenCompraDTO> detalles) {
        this.detalles = detalles;
    }
    
}
