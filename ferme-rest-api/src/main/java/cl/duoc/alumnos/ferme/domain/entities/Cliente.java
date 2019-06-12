package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.dto.ClienteDTO;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static Logger LOG = LoggerFactory.getLogger(Cliente.class);
    
    @Id
    @Column(name = "ID_CLIENTE")
    @SequenceGenerator(name = "cliente_seq", sequenceName = "SEQ_CLIENTE", initialValue = 1, allocationSize = Ferme.DEFAULT_HIBERNATE_SEQUENCES_ALLOCATION_SIZE)
    @GeneratedValue(generator = "cliente_seq", strategy = GenerationType.AUTO)
    private Integer id;
    
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA", insertable = true, updatable = true)
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Persona persona;

    public Cliente() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
    public ClienteDTO toDTO() {
        LOG.debug("toDTO");
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(id);
        dto.setIdPersona(persona.getId());
        dto.setNombreCompletoPersona(persona.getNombreCompleto());
        dto.setRutPersona(persona.getRut());
        
        String direccion = persona.getDireccion();
        String email = persona.getEmail();
        Long fono1 = persona.getFono1();
        Long fono2 = persona.getFono2();
        Long fono3 = persona.getFono3();
        
        if (direccion != null) {
          dto.setDireccionPersona(direccion);
        }
        
        if (email != null) {
          dto.setEmailPersona(email);
        }
        
        if (fono1 != null) {
          dto.setFonoPersona1(fono1);
        }
        
        if (fono2 != null) {
          dto.setFonoPersona2(fono2);
        }
        
        if (fono3 != null) {
          dto.setFonoPersona3(fono3);
        }
        
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
        return (Objects.equals(this.id, other.getId()));
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Cliente[ id=" + id + " ]";
    }
    
}
