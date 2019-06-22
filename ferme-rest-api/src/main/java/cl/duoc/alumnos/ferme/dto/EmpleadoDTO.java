package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.Cargo;
import cl.duoc.alumnos.ferme.domain.entities.Empleado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Benjamin Guillermo
 */
public class EmpleadoDTO extends PersonaDTO {
    private final static Logger LOG = LoggerFactory.getLogger(EmpleadoDTO.class);
    
    private Integer idEmpleado;
    private Integer idCargo;
    private String descripcionCargo;

    public EmpleadoDTO() {
        super();
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
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
    
    public Empleado toEntity() {
        Empleado entity = new Empleado();
        if (idEmpleado != null && idEmpleado != 0) {
            entity.setId(idEmpleado);
        }
        
        entity.setPersona(this.personaToEntity());
        
        Cargo cargoEntity = new Cargo();
        cargoEntity.setId(idCargo);
        entity.setCargo(cargoEntity);
        
        return entity;
    }

    @Override
    public String toString() {
        return "EmpleadoDTO{" + "idEmpleado=" + idEmpleado + ", idCargo=" + idCargo + ", " + super.toString() + '}';
    }

    
    
}
