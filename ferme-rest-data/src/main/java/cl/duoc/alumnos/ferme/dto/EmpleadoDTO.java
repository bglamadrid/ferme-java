package cl.duoc.alumnos.ferme.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.duoc.alumnos.ferme.entities.Cargo;
import cl.duoc.alumnos.ferme.entities.Empleado;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public class EmpleadoDTO extends PersonaDTO {
    @SuppressWarnings("unused")
	private final static Logger LOG = LoggerFactory.getLogger(EmpleadoDTO.class);
    
    private Integer idEmpleado;
    private Integer idCargo;
    private String descripcionCargo;

    public EmpleadoDTO() {
        super();
    }

    public EmpleadoDTO(Integer idPersona, String nombreCompletoPersona, String rutPersona, String direccionPersona,
			String emailPersona, Long fonoPersona1, Long fonoPersona2, Long fonoPersona3) {
		super(idPersona, nombreCompletoPersona, rutPersona, direccionPersona, emailPersona, fonoPersona1, fonoPersona2,
				fonoPersona3);
	}

	public EmpleadoDTO(Integer idPersona, String nombreCompletoPersona, String rutPersona, String direccionPersona,
			String emailPersona, Long fonoPersona1, Long fonoPersona2, Long fonoPersona3, Integer idEmpleado,
			Integer idCargo, String descripcionCargo) {
		super(idPersona, nombreCompletoPersona, rutPersona, direccionPersona, emailPersona, fonoPersona1, fonoPersona2,
				fonoPersona3);
		this.idEmpleado = idEmpleado;
		this.idCargo = idCargo;
		this.descripcionCargo = descripcionCargo;
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
