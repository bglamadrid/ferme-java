package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.dto.EmpleadoDTO;
import java.io.Serializable;
import java.util.Objects;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;

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
    @SequenceGenerator(name = "empleado_seq", sequenceName = "SEQ_EMPLEADO", initialValue = 1, allocationSize = Ferme.DEFAULT_HIBERNATE_SEQUENCES_ALLOCATION_SIZE)
    @GeneratedValue(generator = "empleado_seq", strategy = GenerationType.AUTO)
    private Integer id;
    
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA", insertable = true, updatable = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Persona persona;
    
    @JoinColumn(name = "ID_CARGO", referencedColumnName = "ID_CARGO", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cargo cargo;

    public Empleado() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public EmpleadoDTO toDTO() {
        EmpleadoDTO dto = new EmpleadoDTO();
        
        dto.setIdEmpleado(id);
        dto.setIdPersona(persona.getId());
        dto.setIdCargo(cargo.getId());
        dto.setRutPersona(persona.getRut());
        dto.setNombreCompletoPersona(persona.getNombreCompleto());
        dto.setDireccionPersona(persona.getDireccion());
        dto.setEmailPersona(persona.getEmail());
        dto.setFonoPersona1(persona.getFono1());
        dto.setFonoPersona2(persona.getFono2());
        dto.setFonoPersona3(persona.getFono3());
        
        return dto;
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
        return (Objects.equals(this.id, other.id));
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Empleado[ id=" + id + " ]";
    }
    
}