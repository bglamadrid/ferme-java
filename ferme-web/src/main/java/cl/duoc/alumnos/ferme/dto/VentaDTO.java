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
import java.util.List;

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
        Venta entity = new Venta();
        if (idVenta != null) {
            entity.setId(idVenta);
        }
        
        entity.setCliente(new Cliente(idCliente));
        entity.setEmpleado(new Empleado(idEmpleado));
        
        DateFormat formateador = new SimpleDateFormat(Ferme.DEFAULT_DATE_FORMAT);
        entity.setFecha(formateador.parse(fechaVenta));
        
        List<DetalleVenta> detallesEntities = new ArrayList<>();
        for (DetalleVentaDTO detalle : detallesVenta) {
            detallesEntities.add(detalle.toEntity());
        }
        entity.setDetalles(detallesEntities);
        
        return entity;
    }
    
}
