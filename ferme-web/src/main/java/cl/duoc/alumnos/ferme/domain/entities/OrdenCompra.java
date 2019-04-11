package cl.duoc.alumnos.ferme.domain.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "ORDEN_COMPRA")
@NamedQueries({
    @NamedQuery(name = "OrdenCompra.findAll", query = "SELECT o FROM OrdenCompra o")})
public class OrdenCompra implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_ORDEN_COMPRA")
    private int idOrdenCompra;
    
    @Column(name = "ID_EMPLEADO")
    private int idEmpleado;
    
    @Column(name = "ESTADO")
    private Character estado;
    
    @Column(name = "FECHA_SOLICITUD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSolicitud;
    
    @Column(name = "FECHA_RECEPCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRecepcion;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ordenCompra")
    private List<DetalleOrdenCompra> detalles;

    public OrdenCompra() {}
    
    public int getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(int idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
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

    public List<DetalleOrdenCompra> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleOrdenCompra> detalles) {
        this.detalles = detalles;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.idOrdenCompra;
        hash = 47 * hash + Objects.hashCode(this.fechaSolicitud);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof OrdenCompra)) {
            return false;
        }
        final OrdenCompra other = (OrdenCompra) object;
        return (this.idOrdenCompra != other.idOrdenCompra);
    }

    

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.OrdenCompra[ idOrdenCompra=" + idOrdenCompra + " ]";
    }
    
}
