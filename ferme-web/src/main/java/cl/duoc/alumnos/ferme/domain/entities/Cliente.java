package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.dto.ClienteDTO;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Benjamin Guillermo
 */
@Entity
@Table(name = "CLIENTE")
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")})
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_CLIENTE")
    private int id;
    
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private Persona persona;

    public Cliente() {
        super();
    }

    public Cliente(int id) {
        super();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
    public ClienteDTO toDTO() {
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(persona.getId());
        dto.setNombreCompletoPersona(persona.getNombreCompleto());
        dto.setRutPersona(persona.getRut());
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
        hash = 89 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cliente)) {
            return false;
        }
        final Cliente other = (Cliente) object;
        return (this.id == other.getId());
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Cliente[ idCliente=" + id + " ]";
    }
    
}
