package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.Rubro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Benjamin Guillermo
 */
public class RubroDTO {
    private final static Logger LOG = LoggerFactory.getLogger(RubroDTO.class);
    
    private Integer idRubro;
    private String descripcionRubro;

    public RubroDTO() {}

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
        try {
            if (idRubro != 0) {
                entity.setId(idRubro);
            }
        } catch (NullPointerException exc) {
            LOG.info("toEntity() - idRubro es null");
        }
        
        entity.setDescripcion(descripcionRubro);
        
        return entity;
    }
    
}
