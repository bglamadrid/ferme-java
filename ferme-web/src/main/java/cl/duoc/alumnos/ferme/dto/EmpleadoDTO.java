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
        try {
            if (idEmpleado != 0) {
                entity.setId(idEmpleado);
            }
        } catch (NullPointerException exc) {
            LOG.info("toEntity() - idEmpleado es null");
        }
        
        entity.setCargo(new Cargo(idCargo));
        entity.setPersona(this.personaToEntity());
        
        return entity;
    }
    
}
