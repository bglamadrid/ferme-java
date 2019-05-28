package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.dto.ProveedorDTO;
import java.io.Serializable;
import java.util.Objects;
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
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cascade;

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
    @Column(name = "ID_PROVEEDOR")
    @SequenceGenerator(name = "proveedor_seq", sequenceName = "SEQ_PROVEEDOR", initialValue = 1, allocationSize = Ferme.DEFAULT_HIBERNATE_SEQUENCES_ALLOCATION_SIZE)
    @GeneratedValue(generator = "proveedor_seq", strategy = GenerationType.AUTO)
    private Integer id;
    
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA", insertable = true, updatable = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Persona persona;
    
    @Size(min = 1, max = 50)
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;

    public Proveedor() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public ProveedorDTO toDTO() {
        ProveedorDTO dto = new ProveedorDTO();
        dto.setIdProveedor(id);
        dto.setRazonSocialProveedor(razonSocial);
        dto.setIdPersona(persona.getId());
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
        int hash = 5;
        hash = 71 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Proveedor)) {
            return false;
        }
        final Proveedor other = (Proveedor) object;
        return (Objects.equals(this.id, other.getId()));
    }
    
    

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Proveedor[ idProveedor=" + id + " ]";
    }
    
}
