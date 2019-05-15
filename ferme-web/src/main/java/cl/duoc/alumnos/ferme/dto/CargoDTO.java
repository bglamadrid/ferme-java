package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.Cargo;

/**
 *
 * @author Benjamin Guillermo
 */
public class CargoDTO {
    
    private Integer idCargo;
    private String descripcionCargo;

    public CargoDTO() {
        super();
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getDescripcionCargo() {
        return descripcionCargo;
    }

    public void setDescripcionCargo(String descripcionCargo) {
        this.descripcionCargo = descripcionCargo;
    }
    
    public Cargo toEntity() {
        Cargo entity = new Cargo();
        if (idCargo != 0) {
            entity.setId(idCargo);
        }
        
        entity.setDescripcion(descripcionCargo);
        
        return entity;
    }
    
}
