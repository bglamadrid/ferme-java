package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.domain.entities.DetalleOrdenCompra;
import cl.duoc.alumnos.ferme.domain.entities.Empleado;
import cl.duoc.alumnos.ferme.domain.entities.OrdenCompra;
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
    
    public OrdenCompra toEntity() {
        OrdenCompra entity = new OrdenCompra();
        try {
            if (idOrdenCompra != 0) {
                entity.setId(idOrdenCompra);
            }
        } catch (NullPointerException exc) {
            LOG.info("toEntity() - idOrdenCompra es null");
        }
        
        entity.setEstado(estadoOrdenCompra.charAt(0));
        
        
        DateFormat formateador = new SimpleDateFormat(Ferme.DEFAULT_DATE_FORMAT);
        try {
            Date fechaSolicitud = formateador.parse(fechaSolicitudOrdenCompra);
            entity.setFechaSolicitud(fechaSolicitud);
        } catch (Exception exc) {
            LOG.warn("toEntity() - 'fechaSolicitudOrdenCompra' es null o posee un valor inválido", exc);
            Date fechaActual = Calendar.getInstance().getTime();
            entity.setFechaSolicitud(fechaActual);
            LOG.info("toEntity() - Se asume fecha y hora actual ("+formateador.format(fechaActual)+") para fecha de solicitud");
        }
        
        try {
            Date fechaRecepcion = formateador.parse(fechaRecepcionOrdenCompra);
            entity.setFechaRecepcion(fechaRecepcion);
        } catch (Exception exc) {
            LOG.warn("toEntity() - 'fechaRecepcionOrdenCompra' es null o posee formato incorrecto", exc);
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
