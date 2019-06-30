package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.RubroDTO;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Entity
@Table(name = "RUBRO")
@NamedQueries({
    @NamedQuery(name = "Rubro.findAll", query = "SELECT r FROM Rubro r")})
public class Rubro implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_RUBRO")
    @SequenceGenerator(name = "rubro_seq", sequenceName = "SEQ_RUBRO", initialValue = 1, allocationSize = FermeConfig.ESPACIO_ASIGNACION_SECUENCIAS_HIBERNATE)
    @GeneratedValue(generator = "rubro_seq", strategy = GenerationType.AUTO)
    private Integer _id;
    
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPCION")
    private String _descripcion;

    public Rubro() {
        super();
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        this._id = id;
    }

    public String getDescripcion() {
        return _descripcion;
    }

    public void setDescripcion(String descripcion) {
        this._descripcion = descripcion;
    }
    
    public RubroDTO toDTO() {
        RubroDTO dto = new RubroDTO();
        dto.setIdRubro(_id);
        dto.setDescripcionRubro(_descripcion);
        
        return dto;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this._id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Rubro)) {
            return false;
        }
        final Rubro other = (Rubro) object;
        return (Objects.equals(this._id, other.getId()));
    }
    
    

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Rubro[ idRubro=" + _id + " ]";
    }

    
}
