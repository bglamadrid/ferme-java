package cl.duoc.alumnos.ferme.dto;

/**
 *
 * @author Benjamin Guillermo
 */
public class PersonaDTO {
    
    private Integer idPersona;
    private String nombreCompletoPersona;
    private String rutPersona;

    public PersonaDTO() {
        super();
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombreCompletoPersona() {
        return nombreCompletoPersona;
    }

    public void setNombreCompletoPersona(String nombreCompletoPersona) {
        this.nombreCompletoPersona = nombreCompletoPersona;
    }

    public String getRutPersona() {
        return rutPersona;
    }

    public void setRutPersona(String rutPersona) {
        this.rutPersona = rutPersona;
    }
    
}
