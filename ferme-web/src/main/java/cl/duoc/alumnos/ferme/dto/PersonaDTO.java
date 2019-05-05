package cl.duoc.alumnos.ferme.dto;

/**
 *
 * @author Benjamin Guillermo
 */
public class PersonaDTO {
    
    private Integer idPersona;
    private String nombreCompletoPersona;
    private String rutPersona;
    private String direccionPersona;
    private String emailPersona;
    private Long fonoPersona1;
    private Long fonoPersona2;
    private Long fonoPersona3;

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

    public String getDireccionPersona() {
        return direccionPersona;
    }

    public void setDireccionPersona(String direccionPersona) {
        this.direccionPersona = direccionPersona;
    }

    public String getEmailPersona() {
        return emailPersona;
    }

    public void setEmailPersona(String emailPersona) {
        this.emailPersona = emailPersona;
    }

    public Long getFonoPersona1() {
        return fonoPersona1;
    }

    public void setFonoPersona1(Long fonoPersona1) {
        this.fonoPersona1 = fonoPersona1;
    }

    public Long getFonoPersona2() {
        return fonoPersona2;
    }

    public void setFonoPersona2(Long fonoPersona2) {
        this.fonoPersona2 = fonoPersona2;
    }

    public Long getFonoPersona3() {
        return fonoPersona3;
    }

    public void setFonoPersona3(Long fonoPersona3) {
        this.fonoPersona3 = fonoPersona3;
    }
    
    
    
}
