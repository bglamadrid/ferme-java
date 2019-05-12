package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.dto.DetalleVentaDTO;
import cl.duoc.alumnos.ferme.dto.VentaDTO;
import cl.duoc.alumnos.ferme.util.FermeDates;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Benjamin Guillermo
 */
@Entity
@Table(name = "VENTA")
@NamedQueries({
    @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v")})
public class Venta implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_VENTA")
    private int id;
    
    @JoinColumn(name = "ID_EMPLEADO", referencedColumnName = "ID_EMPLEADO", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empleado empleado;
    
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_CLIENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cliente cliente;
    
    @Column(name = "TIPO_VENTA")
    private Character tipoVenta;
    
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    
    @Column(name = "SUBTOTAL")
    private long subtotal;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venta", fetch = FetchType.LAZY)
    private List<DetalleVenta> detalles;

    public Venta() {
        super();
    }

    public Venta(int id) {
        super();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Character getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(Character tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(long subtotal) {
        this.subtotal = subtotal;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }
    
    public VentaDTO toDTO() {
        VentaDTO dto = new VentaDTO();
        
        final int _id = id;
        final int _clienteId = cliente.getId();
        final int _empleadoId = empleado.getId();
        final String _tipoVenta = tipoVenta.toString();
        final long _subtotal = subtotal;
        final List<DetalleVentaDTO> _detalles = this.detallesToDTO();
        final String _fechaVenta = FermeDates.fechaToString(fecha);
        
        dto.setIdVenta(_id);
        dto.setIdCliente(_clienteId);
        dto.setIdEmpleado(_empleadoId);
        dto.setTipoVenta(_tipoVenta);
        dto.setSubtotalVenta(_subtotal);
        dto.setDetallesVenta(_detalles);
        dto.setFechaVenta(_fechaVenta);
        
        return dto;
    }
    
    private List<DetalleVentaDTO> detallesToDTO() {
        List<DetalleVentaDTO> detallesDTO = new ArrayList<>();
        for (DetalleVenta dtl : detalles) {
            detallesDTO.add(dtl.toDTO());
        }
        return detallesDTO;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Venta)) {
            return false;
        }
        final Venta other = (Venta) object;
        return (this.id != other.getId());
    }
    
    

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Venta[ idVenta=" + id + " ]";
    }
    
}
