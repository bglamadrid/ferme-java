package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.Cliente;
import cl.duoc.alumnos.ferme.domain.entities.DetalleVenta;
import cl.duoc.alumnos.ferme.domain.entities.Empleado;
import cl.duoc.alumnos.ferme.domain.entities.Venta;
import cl.duoc.alumnos.ferme.util.FermeDates;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Benjamin Guillermo
 */
public class VentaDTO {
    private final static Logger LOG = LoggerFactory.getLogger(VentaDTO.class);
    
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
    
    public Venta toEntity() {
        
        Venta entity = new Venta();
        try {
            final Integer _id = idVenta;
            if (_id != 0) {
                entity.setId(_id);
            }
        } catch (NullPointerException exc) {
            LOG.info("toEntity - idVenta es null");
        }
        
        Date _fechaVenta = FermeDates.fechaStringToDate(fechaVenta);
        
        entity.setFecha(_fechaVenta);
        
        if (tipoVenta != null && !tipoVenta.isEmpty()) {
            entity.setTipoVenta(tipoVenta.charAt(0));
        } else {
            entity.setTipoVenta('B');
        }
        
        Cliente entityCliente = new Cliente();
        entityCliente.setId(idCliente);
        entity.setCliente(entityCliente);
        
        if (idEmpleado != null && idEmpleado != 0) {
            Empleado entityEmpleado = new Empleado();
            entityEmpleado.setId(idEmpleado);
            entity.setEmpleado(entityEmpleado);
        }
        
        List<DetalleVenta> detallesEntities = this.detallesToEntity();
        detallesEntities.forEach((dtl) -> {dtl.setVenta(entity);});
        entity.setDetalles(detallesEntities);
        
        return entity;
    }

    private List<DetalleVenta> detallesToEntity() {
        List<DetalleVenta> detallesEntities = new ArrayList<>();
        if (detallesVenta != null && !detallesVenta.isEmpty()) {
            for (DetalleVentaDTO detalle : detallesVenta) {
                detallesEntities.add(detalle.toEntity());
            }
        }
        return detallesEntities;
    }

    @Override
    public String toString() {
        return "VentaDTO{" + "idVenta=" + idVenta + ", tipoVenta=" + tipoVenta + ", fechaVenta=" + fechaVenta + ", subtotalVenta=" + subtotalVenta + ", detallesVenta=" + detallesVenta + ", idEmpleado=" + idEmpleado + ", idCliente=" + idCliente + '}';
    }
    
}
