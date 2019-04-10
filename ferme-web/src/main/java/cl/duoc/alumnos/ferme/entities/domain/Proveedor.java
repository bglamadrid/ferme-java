package cl.duoc.alumnos.ferme.entities.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Benjamin Guillermo
 */
@Entity
@Table(name = "PROVEEDOR")
@NamedQueries({
    @NamedQuery(name = "Proveedor.findAll", query = "SELECT p FROM Proveedor p")})
public class Proveedor implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_PERSONA")
    private int idPersona;
    
    @Column(name = "ID_PROVEEDOR")
    private int idProveedor;
    
    @Size(min = 1, max = 50)
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    
    @Column(name = "TELEFONO")
    private long telefono;
    
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Persona persona;
    
    @JoinColumn(name = "ID_RUBRO", referencedColumnName = "ID_RUBRO")
    @ManyToOne(optional = false)
    private Rubro rubro;

    public Proveedor() {}

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.idPersona;
        hash = 71 * hash + this.idProveedor;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Proveedor)) {
            return false;
        }
        final Proveedor other = (Proveedor) object;
        return (this.idPersona == other.idPersona || this.idProveedor == other.idProveedor);
    }
    
    

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Proveedor[ idPersona=" + idPersona + " ]";
    }
    
}
