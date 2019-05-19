package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.dto.RubroDTO;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Benjamin Guillermo
 */
@Entity
@Table(name = "RUBRO")
@NamedQueries({
    @NamedQuery(name = "Rubro.findAll", query = "SELECT r FROM Rubro r")})
public class Rubro implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_RUBRO")
    private Integer id;
    
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public Rubro() {
        super();
    }

    public Rubro(int id) {
        super();
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public RubroDTO toDTO() {
        RubroDTO dto = new RubroDTO();
        
        dto.setIdRubro(id);
        dto.setDescripcionRubro(descripcion);
        
        return dto;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Rubro)) {
            return false;
        }
        final Rubro other = (Rubro) object;
        return (this.id == other.getId());
    }
    
    

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Rubro[ idRubro=" + id + " ]";
    }

    
}
