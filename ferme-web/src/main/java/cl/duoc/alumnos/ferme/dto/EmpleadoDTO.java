package cl.duoc.alumnos.ferme.dto;

/**
 *
 * @author Benjamin Guillermo
 */
public class EmpleadoDTO {
    
    private Integer idEmpleado;
    private PersonaDTO persona;
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

    public PersonaDTO getPersona() {
        return persona;
    }

    public void setPersona(PersonaDTO idPersona) {
        this.persona = idPersona;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }
    
}
