package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.domain.entities.DetalleOrdenCompra;
import cl.duoc.alumnos.ferme.domain.entities.Empleado;
import cl.duoc.alumnos.ferme.domain.entities.OrdenCompra;
import cl.duoc.alumnos.ferme.util.FermeDates;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Benjamin Guillermo
 */
public class OrdenCompraDTO {
    private final static Logger LOG = LoggerFactory.getLogger(OrdenCompraDTO.class);
    
    private Integer idOrdenCompra;
    private Integer idEmpleado;
    private String nombrePersonaEmpleado;
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

    public String getNombrePersonaEmpleado() {
        return nombrePersonaEmpleado;
    }

    public void setNombrePersonaEmpleado(String nombrePersonaEmpleado) {
        this.nombrePersonaEmpleado = nombrePersonaEmpleado;
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
    
    public OrdenCompra toEntity() {
        OrdenCompra entity = new OrdenCompra();
        if (idOrdenCompra != null && idOrdenCompra != 0) {
            entity.setId(idOrdenCompra);
        }
        
        if (estadoOrdenCompra != null && !estadoOrdenCompra.isEmpty()) {
            entity.setEstado(estadoOrdenCompra.charAt(0));
        } else {
            entity.setEstado(Ferme.ORDEN_COMPRA_ESTADO_SOLICITADO);
        }
        
        Date fSolicitud = FermeDates.fechaStringToDate(fechaSolicitudOrdenCompra);
        entity.setFechaSolicitud(fSolicitud);
        
        if (fechaRecepcionOrdenCompra != null && !fechaRecepcionOrdenCompra.isEmpty()) {
            Date fechaRecepcion = FermeDates.fechaStringToDate(fechaRecepcionOrdenCompra);
            entity.setFechaRecepcion(fechaRecepcion);
        }
        
        List<DetalleOrdenCompra> _detallesEntities = this.detallesToEntity();
        _detallesEntities.forEach((dtl) -> {dtl.setOrdenCompra(entity);});
        entity.setDetalles(_detallesEntities);
        
        return entity;
    }

    private List<DetalleOrdenCompra> detallesToEntity() {
        List<DetalleOrdenCompra> detallesEntities = new ArrayList<>();
        if (detallesOrdenCompra != null && !detallesOrdenCompra.isEmpty()) {
            for (DetalleOrdenCompraDTO detalle : detallesOrdenCompra) {
                DetalleOrdenCompra detalleEntity = detalle.toEntity();
                detallesEntities.add(detalleEntity);
            }
        }
        return detallesEntities;
    }

    @Override
    public String toString() {
        return "OrdenCompraDTO{" + "idOrdenCompra=" + idOrdenCompra + ", idEmpleado=" + idEmpleado + ", estadoOrdenCompra=" + estadoOrdenCompra + ", fechaSolicitudOrdenCompra=" + fechaSolicitudOrdenCompra + ", fechaRecepcionOrdenCompra=" + fechaRecepcionOrdenCompra + ", detallesOrdenCompra=" + detallesOrdenCompra + '}';
    }
    
}
