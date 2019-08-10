package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.Rubro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public class RubroDTO {
    @SuppressWarnings("unused")
	private final static Logger LOG = LoggerFactory.getLogger(RubroDTO.class);
    
    private Integer idRubro;
    private String descripcionRubro;

    public RubroDTO() {
        super();
    }

    public Integer getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(Integer idRubro) {
        this.idRubro = idRubro;
    }

    public String getDescripcionRubro() {
        return descripcionRubro;
    }

    public void setDescripcionRubro(String descripcionRubro) {
        this.descripcionRubro = descripcionRubro;
    }
    
    public Rubro toEntity() {
        Rubro entity = new Rubro();
        if (idRubro != null && idRubro != 0) {
            entity.setId(idRubro);
        }
        entity.setDescripcion(descripcionRubro);
        
        return entity;
    }

    @Override
    public String toString() {
        return "RubroDTO{" + "idRubro=" + idRubro + ", descripcionRubro=" + descripcionRubro + '}';
    }
    
}
