package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.domain.entities.DetalleOrdenCompra;
import cl.duoc.alumnos.ferme.domain.entities.Empleado;
import cl.duoc.alumnos.ferme.domain.entities.OrdenCompra;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    
    public OrdenCompra toEntity() throws ParseException {
        OrdenCompra entity = new OrdenCompra();
        entity.setId(idOrdenCompra);
        
        entity.setEmpleado(new Empleado(idEmpleado));
        entity.setEstado(estadoOrdenCompra.charAt(0));
        
        DateFormat formateador = new SimpleDateFormat(Ferme.DEFAULT_DATE_FORMAT);
        entity.setFechaSolicitud(formateador.parse(fechaSolicitudOrdenCompra));
        if (fechaRecepcionOrdenCompra != null && !fechaRecepcionOrdenCompra.isEmpty()) {
            entity.setFechaRecepcion(formateador.parse(fechaRecepcionOrdenCompra));
        }
        
        List<DetalleOrdenCompra> _detallesEntities = this.detallesToEntity();
        entity.setDetalles(_detallesEntities);
        
        return entity;
    }

    private List<DetalleOrdenCompra> detallesToEntity() {
        List<DetalleOrdenCompra> detallesEntities = new ArrayList<>();
        if (detallesOrdenCompra != null && !detallesOrdenCompra.isEmpty()) {
            for (DetalleOrdenCompraDTO detalle : detallesOrdenCompra) {
                detallesEntities.add(detalle.toEntity());
            }
        }
        return detallesEntities;
    }
    
}
