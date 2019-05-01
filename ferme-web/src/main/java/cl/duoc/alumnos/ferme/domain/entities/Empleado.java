package cl.duoc.alumnos.ferme.domain.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Benjamin Guillermo
 */
@Entity
@Table(name = "EMPLEADO")
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e")})
public class Empleado implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_EMPLEADO")
    private int id;
    
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA")
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private Persona persona;
    
    @JoinColumn(name = "ID_CARGO", referencedColumnName = "ID_CARGO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cargo cargo;

    public Empleado() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        hash = 59 * hash + Objects.hashCode(this.cargo);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Empleado)) {
            return false;
        }
        final Empleado other = (Empleado) object;
        return (this.id == other.id && this.persona.getId()== other.getPersona().getId());
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Empleado[ idEmpleado=" + id + " ]";
    }
    
}
