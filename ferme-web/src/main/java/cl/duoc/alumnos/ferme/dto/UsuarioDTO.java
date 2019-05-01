package cl.duoc.alumnos.ferme.dto;

/**
 *
 * @author Benjamin Guillermo
 */
public class UsuarioDTO {
    
    private Integer idUsuario;
    private String nombreUsuario;
    private String claveUsuario;
    private String fechaCreacionUsuario;
    private PersonaDTO persona;

    public UsuarioDTO() {}

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getClaveUsuario() {
        return claveUsuario;
    }

    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    public String getFechaCreacionUsuario() {
        return fechaCreacionUsuario;
    }

    public void setFechaCreacionUsuario(String fechaCreacionUsuario) {
        this.fechaCreacionUsuario = fechaCreacionUsuario;
    }

    public PersonaDTO getPersona() {
        return persona;
    }

    public void setPersona(PersonaDTO persona) {
        this.persona = persona;
    }
    
}
