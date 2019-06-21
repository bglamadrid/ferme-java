package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.Cargo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Benjamin Guillermo
 */
public class CargoDTO {
    private final static Logger LOG = LoggerFactory.getLogger(CargoDTO.class);
    
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
        if (idCargo != null && idCargo != 0) {
            entity.setId(idCargo);
        }
        entity.setDescripcion(descripcionCargo);
        
        return entity;
    }

    @Override
    public String toString() {
        return "CargoDTO{" + "idCargo=" + idCargo + ", descripcionCargo=" + descripcionCargo + '}';
    }
    
}
