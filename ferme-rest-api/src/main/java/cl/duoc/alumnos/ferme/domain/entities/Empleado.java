package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.EmpleadoDTO;
import cl.duoc.alumnos.ferme.util.PersonaConverter;
import java.io.Serializable;
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
    @SequenceGenerator(name = "empleado_seq", sequenceName = "SEQ_EMPLEADO", initialValue = 1, allocationSize = FermeConfig.DEFAULT_HIBERNATE_SEQUENCES_ALLOCATION_SIZE)
    @GeneratedValue(generator = "empleado_seq", strategy = GenerationType.AUTO)
    private Integer _id;
    
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA")
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Persona _persona;
    
    @JoinColumn(name = "ID_CARGO", referencedColumnName = "ID_CARGO")
    @ManyToOne(cascade = CascadeType.DETACH, optional = false, fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    private Cargo _cargo;

    public Empleado() {
        super();
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        this._id = id;
    }

    public Cargo getCargo() {
        return _cargo;
    }

    public void setCargo(Cargo cargo) {
        this._cargo = cargo;
    }

    public Persona getPersona() {
        return _persona;
    }

    public void setPersona(Persona persona) {
        this._persona = persona;
    }

    public EmpleadoDTO toDTO() {
        EmpleadoDTO dto = new EmpleadoDTO();
        Cargo cargoEntity = getCargo();
        dto = PersonaConverter.cargarDatosPersonaEnDTO(_persona, dto);
        dto.setIdEmpleado(_id);
        dto.setIdPersona(_persona.getId());
        
        dto.setIdCargo(cargoEntity.getId());
        dto.setDescripcionCargo(cargoEntity.getDescripcion());
        
        return dto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this._id;
        hash = 59 * hash + Objects.hashCode(this._cargo);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Empleado)) {
            return false;
        }
        final Empleado other = (Empleado) object;
        return (Objects.equals(this._id, other._id));
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Empleado[ id=" + _id + " ]";
    }
    
}
