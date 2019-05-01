package cl.duoc.alumnos.ferme.dto;

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
    
}
