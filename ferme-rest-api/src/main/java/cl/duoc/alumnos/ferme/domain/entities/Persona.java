package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.PersonaDTO;
import cl.duoc.alumnos.ferme.services.PersonasService;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cascade;

/** Representa un registro de la tabla PERSONA.
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Entity
@Table(name = "PERSONA")
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p")})
public class Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_PERSONA")
    @SequenceGenerator(name = "persona_seq", sequenceName = "SEQ_PERSONA", initialValue = 1, allocationSize = FermeConfig.ESPACIO_ASIGNACION_SECUENCIAS_HIBERNATE)
    @GeneratedValue(generator = "persona_seq", strategy = GenerationType.AUTO)
    private Integer _id;

    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE_COMPLETO")
    private String _nombreCompleto;
    
    @Size(min = 1, max = 20)
    @Column(name = "RUT")
    private String _rut;
    
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Email inválido")
    @Size(max = 100)
    @Column(name = "EMAIL")
    private String _email;
    
    @Size(max = 200)
    @Column(name = "DIRECCION")
    private String _direccion;
    
    @Column(name = "FONO1")
    private Long _fono1;
    
    @Column(name = "FONO2")
    private Long _fono2;
    
    @Column(name = "FONO3")
    private Long _fono3;
    
    @OneToOne(optional = true, fetch = FetchType.LAZY, mappedBy = "_persona")
    private Cliente _cliente;
    
    @OneToOne(optional = true, fetch = FetchType.LAZY, mappedBy = "_persona")
    private Empleado _empleado;
    
    @OneToOne(optional = true, fetch = FetchType.LAZY, mappedBy = "_persona")
    private Proveedor _proveedor;

    public Persona() {
        super();
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        this._id = id;
    }

    public String getNombreCompleto() {
        return _nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this._nombreCompleto = nombreCompleto;
    }

    public String getRut() {
        return _rut;
    }

    public void setRut(String rut) {
        this._rut = rut;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String email) {
        this._email = email;
    }

    public String getDireccion() {
        return _direccion;
    }

    public void setDireccion(String direccion) {
        this._direccion = direccion;
    }

    public Long getFono1() {
        return _fono1;
    }

    public void setFono1(Long fono1) {
        this._fono1 = fono1;
    }

    public Long getFono2() {
        return _fono2;
    }

    public void setFono2(Long fono2) {
        this._fono2 = fono2;
    }

    public Long getFono3() {
        return _fono3;
    }

    public void setFono3(Long fono3) {
        this._fono3 = fono3;
    }
    
    
    public boolean hasCliente() {
        return (this._cliente != null);
    }

    public boolean hasEmpleado() {
        return (this._empleado != null);
    }

    public boolean hasProveedor() {
        return (this._proveedor != null);
    }
    
    public Cliente getCliente() {
        return this._cliente;
    }

    public Empleado getEmpleado() {
        return this._empleado;
    }

    public Proveedor getProveedor() {
        return this._proveedor;
    }
    
    public PersonaDTO toDTO() {        
        return PersonasService.cargarDatosPersonaEnDTO(this, new PersonaDTO());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this._id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Persona)) {
            return false;
        }
        final Persona other = (Persona) object;
        return (Objects.equals(this._id, other.getId()));
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Persona[ id=" + _id + " ]";
    }
    
}
