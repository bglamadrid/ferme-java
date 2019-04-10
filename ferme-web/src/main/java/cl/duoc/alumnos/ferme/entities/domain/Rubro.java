package cl.duoc.alumnos.ferme.entities.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Benjamin Guillermo
 */
@Entity
@Table(name = "RUBRO")
@NamedQueries({
    @NamedQuery(name = "Rubro.findAll", query = "SELECT r FROM Rubro r")})
public class Rubro implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_RUBRO")
    private int idRubro;
    
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rubro")
    private List<Proveedor> proveedores;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRubro")
    private List<FamiliaProducto> familiasProductos;

    public Rubro() {}

    public int getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(int idRubro) {
        this.idRubro = idRubro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    public List<FamiliaProducto> getFamiliasProductos() {
        return familiasProductos;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.idRubro;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Rubro)) {
            return false;
        }
        final Rubro other = (Rubro) object;
        return (this.idRubro == other.idRubro);
    }
    
    

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Rubro[ idRubro=" + idRubro + " ]";
    }
    
}
