package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.Rubro;

/**
 *
 * @author Benjamin Guillermo
 */
public class RubroDTO {
    
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
        if (idRubro != 0) {
            entity.setId(idRubro);
        }
        
        entity.setDescripcion(descripcionRubro);
        
        return entity;
    }
    
}
