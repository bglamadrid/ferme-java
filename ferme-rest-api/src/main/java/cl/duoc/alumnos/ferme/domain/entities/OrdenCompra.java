package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.DetalleOrdenCompraDTO;
import cl.duoc.alumnos.ferme.dto.OrdenCompraDTO;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;

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
    @SequenceGenerator(name = "orden_compra_seq", sequenceName = "SEQ_ORDEN_COMPRA", initialValue = 1, allocationSize = FermeConfig.DEFAULT_HIBERNATE_SEQUENCES_ALLOCATION_SIZE)
    @GeneratedValue(generator = "orden_compra_seq", strategy = GenerationType.AUTO)
    private Integer _id;
    
    @Column(name = "ESTADO")
    private Character _estado;
    
    @Column(name = "FECHA_SOLICITUD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date _fechaSolicitud;
    
    @Column(name = "FECHA_RECEPCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date _fechaRecepcion;
    
    @JoinColumn(name = "ID_EMPLEADO", referencedColumnName = "ID_EMPLEADO")
    @OneToOne(cascade = CascadeType.DETACH, optional = false, fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    private Empleado _empleado;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "_ordenCompra", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<DetalleOrdenCompra> _detalles;

    public OrdenCompra() {
        super();
    }
    
    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        this._id = id;
    }

    public Character getEstado() {
        return _estado;
    }

    public void setEstado(Character estado) {
        this._estado = estado;
    }

    public Date getFechaSolicitud() {
        return _fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this._fechaSolicitud = fechaSolicitud;
    }

    public Date getFechaRecepcion() {
        return _fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this._fechaRecepcion = fechaRecepcion;
    }

    public Empleado getEmpleado() {
        return _empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this._empleado = empleado;
    }

    public List<DetalleOrdenCompra> getDetalles() {
        return _detalles;
    }

    public void setDetalles(List<DetalleOrdenCompra> detalles) {
        this._detalles = detalles;
    }
    
    public OrdenCompraDTO toDTO(boolean simple) {
        OrdenCompraDTO dto = new OrdenCompraDTO();
        dto.setIdOrdenCompra(_id);
        dto.setEstadoOrdenCompra(_estado.toString());
        dto.setFechaSolicitudOrdenCompra(FermeDates.fechaToString(_fechaSolicitud));
        
        Empleado empleadoEntity = getEmpleado();
        Persona empleadoPersonaEntity = empleadoEntity.getPersona();
        dto.setIdEmpleado(empleadoEntity.getId());
        dto.setNombreEmpleado(empleadoPersonaEntity.getNombreCompleto());
        dto.setRutEmpleado(empleadoPersonaEntity.getRut());
        
        if (_fechaRecepcion != null) {
            dto.setFechaRecepcionOrdenCompra(FermeDates.fechaToString(_fechaRecepcion));
        }
        
        if (!simple) {
            List<DetalleOrdenCompraDTO> detallesDTO = new ArrayList<>();
            for (DetalleOrdenCompra detalle : _detalles) {
                detallesDTO.add(detalle.toDTO());
            }
            dto.setDetallesOrdenCompra(detallesDTO);
        }
        
        return dto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this._id;
        hash = 47 * hash + Objects.hashCode(this._fechaSolicitud);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof OrdenCompra)) {
            return false;
        }
        final OrdenCompra other = (OrdenCompra) object;
        return (this._id != other.getId());
    }

   
    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.OrdenCompra[ idOrdenCompra=" + _id + " ]";
    }
    
}
