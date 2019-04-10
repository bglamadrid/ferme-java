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
@Table(name = "CARGO")
@NamedQueries({
    @NamedQuery(name = "Cargo.findAll", query = "SELECT c FROM Cargo c")})
public class Cargo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_CARGO")
    private int idCargo;
    
    @Size(min = 1, max = 100)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCargo")
    private List<Empleado> empleados;

    public Cargo() {}

    public int getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.idCargo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cargo)) {
            return false;
        }
        Cargo other = (Cargo) object;
        return (this.idCargo == other.idCargo);
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Cargo[ idCargo=" + idCargo + " ]";
    }
    
}
