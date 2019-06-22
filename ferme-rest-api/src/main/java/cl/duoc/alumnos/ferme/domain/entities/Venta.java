package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.DetalleVentaDTO;
import cl.duoc.alumnos.ferme.dto.VentaDTO;
import cl.duoc.alumnos.ferme.util.FermeDates;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Entity
@Table(name = "VENTA")
@NamedQueries({
    @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v")})
public class Venta implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_VENTA")
    @SequenceGenerator(name = "venta_seq", sequenceName = "SEQ_VENTA", initialValue = 1, allocationSize = FermeConfig.DEFAULT_HIBERNATE_SEQUENCES_ALLOCATION_SIZE)
    @GeneratedValue(generator = "venta_seq", strategy = GenerationType.AUTO)
    private Integer id;
    
    @JoinColumn(name = "ID_EMPLEADO", referencedColumnName = "ID_EMPLEADO", insertable = true, updatable = true)
    @ManyToOne(cascade = CascadeType.DETACH,optional = true, fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    private Empleado empleado;
    
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_CLIENTE", insertable = true, updatable = true)
    @ManyToOne(cascade = CascadeType.DETACH, optional = false, fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    private Cliente cliente;
    
    @Column(name = "TIPO_VENTA")
    private Character tipoVenta;
    
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    
    @Column(name = "SUBTOTAL")
    private long subtotal;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "_venta", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<DetalleVenta> detalles;

    public Venta() {
        super();
    }

    public Integer getId() {
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
    
    public VentaDTO toDTO(boolean simple) {
        VentaDTO dto = new VentaDTO();
        Cliente clienteEntity = getCliente();
        Persona clientePersonaEntity = clienteEntity.getPersona();
        Empleado empleadoEntity = getEmpleado();
        dto.setIdVenta(id);
        dto.setTipoVenta(tipoVenta.toString());
        dto.setSubtotalVenta(subtotal);
        
        final String fVenta = FermeDates.fechaToString(fecha);
        dto.setFechaVenta(fVenta);
        
        
        dto.setIdCliente(clienteEntity.getId());
        dto.setNombreCompletoCliente(clientePersonaEntity.getNombreCompleto());
        dto.setRutCliente(clientePersonaEntity.getRut());
        
        if (empleadoEntity != null) {
            Persona empleadoPersonaEntity = empleadoEntity.getPersona();
            dto.setIdEmpleado(empleadoEntity.getId());
            dto.setNombreCompletoEmpleado(empleadoPersonaEntity.getNombreCompleto());
        }
        
        if (!simple) {
            List<DetalleVentaDTO> _detalles = this.detallesToDTO();
            dto.setDetallesVenta(_detalles);
        }
        
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
        return (!Objects.equals(this.id, other.getId()));
    }
    
    

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Venta[ idVenta=" + id + " ]";
    }
    
}
