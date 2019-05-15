package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.Cargo;
import cl.duoc.alumnos.ferme.domain.entities.Empleado;

/**
 *
 * @author Benjamin Guillermo
 */
public class EmpleadoDTO extends PersonaDTO {
    
    private Integer idEmpleado;
    private Integer idCargo;

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
    
    public Empleado toEntity() {
        Empleado entity = new Empleado();
        if (idEmpleado != 0) {
            entity.setId(idEmpleado);
        }
        
        entity.setCargo(new Cargo(idCargo));
        entity.setPersona(this.personaToEntity());
        
        return entity;
    }
    
}
