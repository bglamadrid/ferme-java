package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.dto.DetalleOrdenCompraDTO;
import cl.duoc.alumnos.ferme.dto.OrdenCompraDTO;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Benjamin Guillermo
 */
@Entity
@Table(name = "ORDEN_COMPRA")
@NamedQueries({
    @NamedQuery(name = "OrdenCompra.findAll", query = "SELECT o FROM OrdenCompra o")})
public class OrdenCompra implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_ORDEN_COMPRA")
    @SequenceGenerator(name = "orden_compra_seq", sequenceName = "SEQ_ORDEN_COMPRA", initialValue = 1, allocationSize = Ferme.DEFAULT_HIBERNATE_SEQUENCES_ALLOCATION_SIZE)
    @GeneratedValue(generator = "orden_compra_seq", strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(name = "ESTADO")
    private Character estado;
    
    @Column(name = "FECHA_SOLICITUD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSolicitud;
    
    @Column(name = "FECHA_RECEPCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRecepcion;
    
    @JoinColumn(name = "ID_EMPLEADO", referencedColumnName = "ID_EMPLEADO")
    @OneToOne
    private Empleado empleado;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ordenCompra", fetch = FetchType.LAZY)
    private List<DetalleOrdenCompra> detalles;

    public OrdenCompra() {
        super();
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<DetalleOrdenCompra> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleOrdenCompra> detalles) {
        this.detalles = detalles;
    }
    
    public OrdenCompraDTO toDTO(boolean simple) {
        OrdenCompraDTO dto = new OrdenCompraDTO();
        
        dto.setIdOrdenCompra(id);
        dto.setIdEmpleado(empleado.getId());
        dto.setEstadoOrdenCompra(estado.toString());
        dto.setFechaSolicitudOrdenCompra(fechaSolicitud.toString());
        
        if (fechaRecepcion != null) {
            DateFormat formateador = new SimpleDateFormat(Ferme.DEFAULT_DATE_FORMAT);
            dto.setFechaRecepcionOrdenCompra(formateador.format(fechaRecepcion));
        }
        
        if (!simple) {
            List<DetalleOrdenCompraDTO> detallesDTO = new ArrayList<>();
            for (DetalleOrdenCompra detalle : detalles) {
                detallesDTO.add(detalle.toDTO());
            }
            dto.setDetallesOrdenCompra(detallesDTO);
        }
        
        return dto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id;
        hash = 47 * hash + Objects.hashCode(this.fechaSolicitud);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof OrdenCompra)) {
            return false;
        }
        final OrdenCompra other = (OrdenCompra) object;
        return (this.id != other.getId());
    }

   
    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.OrdenCompra[ idOrdenCompra=" + id + " ]";
    }
    
}
