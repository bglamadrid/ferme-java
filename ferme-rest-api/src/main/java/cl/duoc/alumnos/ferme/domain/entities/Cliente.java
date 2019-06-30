package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.ClienteDTO;
import cl.duoc.alumnos.ferme.services.PersonasService;
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

/** Representa un registro de la tabla CLIENTE.
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Entity
@Table(name = "CLIENTE")
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")})
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_CLIENTE")
    @SequenceGenerator(name = "cliente_seq", sequenceName = "SEQ_CLIENTE", initialValue = 1, allocationSize = FermeConfig.ESPACIO_ASIGNACION_SECUENCIAS_HIBERNATE)
    @GeneratedValue(generator = "cliente_seq", strategy = GenerationType.AUTO)
    private Integer _id;
    
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA", insertable = true, updatable = true)
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Persona _persona;

    public Cliente() {
        super();
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        this._id = id;
    }

    public Persona getPersona() {
        return _persona;
    }

    public void setPersona(Persona persona) {
        this._persona = persona;
    }
    
    public ClienteDTO toDTO() {
        ClienteDTO dto = new ClienteDTO();
        dto = PersonasService.cargarDatosPersonaEnDTO(_persona, dto);
        dto.setIdCliente(_id);
        
        return dto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this._id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cliente)) {
            return false;
        }
        final Cliente other = (Cliente) object;
        return (Objects.equals(this._id, other.getId()));
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Cliente[ id=" + _id + " ]";
    }
    
}
