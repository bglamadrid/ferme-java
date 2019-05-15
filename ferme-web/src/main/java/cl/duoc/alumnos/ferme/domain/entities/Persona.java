package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.dto.PersonaDTO;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Benjamin Guillermo
 */
@Entity
@Table(name = "PERSONA")
@SequenceGenerator( sequenceName = "SEQ_PERSONA", name = "PersonaIDGenerator" )
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p")})
public class Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "PersonaIDGenerator" )
    @Column(name = "ID_PERSONA")
    private int id;

    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE_COMPLETO")
    private String nombreCompleto;
    
    @Size(min = 1, max = 20)
    @Column(name = "RUT")
    private String rut;
    
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Email inválido")
    @Size(max = 100)
    @Column(name = "EMAIL")
    private String email;
    
    @Size(max = 200)
    @Column(name = "DIRECCION")
    private String direccion;
    
    @Column(name = "FONO1")
    private Long fono1;
    
    @Column(name = "FONO2")
    private Long fono2;
    
    @Column(name = "FONO3")
    private Long fono3;

    public Persona() {
        super();
    }

    public Persona(int id) {
        super();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getFono1() {
        return fono1;
    }

    public void setFono1(Long fono1) {
        this.fono1 = fono1;
    }

    public Long getFono2() {
        return fono2;
    }

    public void setFono2(Long fono2) {
        this.fono2 = fono2;
    }

    public Long getFono3() {
        return fono3;
    }

    public void setFono3(Long fono3) {
        this.fono3 = fono3;
    }
    
    public PersonaDTO toDTO() {
        PersonaDTO dto = new PersonaDTO();
        
        dto.setIdPersona(id);
        dto.setRutPersona(rut);
        dto.setNombreCompletoPersona(nombreCompleto);
        dto.setDireccionPersona(direccion);
        dto.setEmailPersona(email);
        dto.setFonoPersona1(fono1);
        dto.setFonoPersona2(fono2);
        dto.setFonoPersona3(fono3);
        
        return dto;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Persona)) {
            return false;
        }
        final Persona other = (Persona) object;
        return (this.id == other.getId());
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Persona[ idPersona=" + id + " ]";
    }
    
}
