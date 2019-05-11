package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.domain.entities.Cliente;
import cl.duoc.alumnos.ferme.domain.entities.DetalleVenta;
import cl.duoc.alumnos.ferme.domain.entities.Empleado;
import cl.duoc.alumnos.ferme.domain.entities.Venta;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Benjamin Guillermo
 */
public class VentaDTO {
    
    private Integer idVenta;
    private String tipoVenta;
    private String fechaVenta;
    private long subtotalVenta;
    private List<DetalleVentaDTO> detallesVenta;
    private Integer idEmpleado;
    private Integer idCliente;

    public VentaDTO() {}

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public long getSubtotalVenta() {
        return subtotalVenta;
    }

    public void setSubtotalVenta(long subtotalVenta) {
        this.subtotalVenta = subtotalVenta;
    }

    public List<DetalleVentaDTO> getDetallesVenta() {
        return detallesVenta;
    }

    public void setDetallesVenta(List<DetalleVentaDTO> detallesVenta) {
        this.detallesVenta = detallesVenta;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }
    
    public Venta toEntity() throws ParseException {
        
        final Integer _idVenta = idVenta;
        final Cliente _cliente = new Cliente(idCliente);
        final Empleado _empleado = new Empleado(idEmpleado);
        Date _fechaVenta = this.fechaStringToDate();
        List<DetalleVenta> detallesEntities = this.detallesToEntity();
        
        Venta entity = new Venta();
        entity.setId(_idVenta);
        entity.setCliente(_cliente);
        entity.setEmpleado(_empleado);
        entity.setFecha(_fechaVenta);
        entity.setDetalles(detallesEntities);
        
        return entity;
    }

    private Date fechaStringToDate() {
        
        Date _fechaVenta; 
        
        try {
            DateFormat formateador = new SimpleDateFormat(Ferme.DEFAULT_DATE_FORMAT);
            _fechaVenta = formateador.parse(fechaVenta);
        } catch (ParseException exc) {
            final Logger LOG = LoggerFactory.getLogger(VentaDTO.class);
            LOG.warn("VentaDTO.fechaStringToDate(): La fecha de venta tiene un formato inválido, no se pudo convertir y es null.", exc);
            _fechaVenta = null;
        }
        
        return _fechaVenta;
        
    }

    private List<DetalleVenta> detallesToEntity() {
        List<DetalleVenta> detallesEntities = new ArrayList<>();
        for (DetalleVentaDTO detalle : detallesVenta) {
            detallesEntities.add(detalle.toEntity());
        }
        return detallesEntities;
    }
    
}
