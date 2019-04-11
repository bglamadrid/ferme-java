package cl.duoc.alumnos.ferme.dto;


/**
 *
 * @author Benjamin Guillermo
 */
public class EmpleadoDTO {
    
    private Integer idPersona;
    private Integer idEmpleado;
    private Integer idCargo;

    public EmpleadoDTO() {}

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
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
    
}
