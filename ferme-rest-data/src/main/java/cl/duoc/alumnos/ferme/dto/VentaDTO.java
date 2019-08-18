package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.entities.Cliente;
import cl.duoc.alumnos.ferme.entities.DetalleVenta;
import cl.duoc.alumnos.ferme.entities.Empleado;
import cl.duoc.alumnos.ferme.entities.Venta;
import cl.duoc.alumnos.ferme.util.FormatoFechas;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public class VentaDTO {
    @SuppressWarnings("unused")
	private final static Logger LOG = LoggerFactory.getLogger(VentaDTO.class);
    
    private Integer idVenta;
    private String tipoVenta;
    private String fechaVenta;
    private long subtotalVenta;
    private List<DetalleVentaDTO> detallesVenta;
    private Integer idEmpleado;
    private String nombreCompletoEmpleado;
    private String rutEmpleado;
    private Integer idCliente;
    private String nombreCompletoCliente;
    private String rutCliente;

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

    public String getNombreCompletoEmpleado() {
        return nombreCompletoEmpleado;
    }

    public void setNombreCompletoEmpleado(String nombreCompletoEmpleado) {
        this.nombreCompletoEmpleado = nombreCompletoEmpleado;
    }

    public String getRutEmpleado() {
        return rutEmpleado;
    }

    public void setRutEmpleado(String rutEmpleado) {
        this.rutEmpleado = rutEmpleado;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCompletoCliente() {
        return nombreCompletoCliente;
    }

    public void setNombreCompletoCliente(String nombreCompletoCliente) {
        this.nombreCompletoCliente = nombreCompletoCliente;
    }

    public String getRutCliente() {
        return rutCliente;
    }

    public void setRutCliente(String rutCliente) {
        this.rutCliente = rutCliente;
    }   
    
    
    public Venta toEntity() {
        Venta entity = new Venta();
        if (idVenta != null && idVenta != 0) {
            entity.setId(idVenta);
        }
        
        if (subtotalVenta != 0) {
            entity.setSubtotal(subtotalVenta);
        }
        
        Date fVenta = FormatoFechas.stringADateLocal(fechaVenta);
        entity.setFecha(fVenta);
        
        if (tipoVenta != null && !tipoVenta.isEmpty()) {
            entity.setTipoVenta(tipoVenta.charAt(0));
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
