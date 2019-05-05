package cl.duoc.alumnos.ferme.dto;

/**
 *
 * @author Benjamin Guillermo
 */
public class ProveedorDTO extends PersonaDTO {
    
    private Integer idProveedor;
    private PersonaDTO persona;
    private String razonSocialProveedor;
    private Long telefonoProveedor;
    private Integer idRubro;

    public ProveedorDTO() {
        super();
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public PersonaDTO getPersona() {
        return persona;
    }

    public void setPersona(PersonaDTO persona) {
        this.persona = persona;
    }

    public String getRazonSocialProveedor() {
        return razonSocialProveedor;
    }

    public void setRazonSocialProveedor(String razonSocialProveedor) {
        this.razonSocialProveedor = razonSocialProveedor;
    }

    public Long getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(Long telefonoProveedor) {
        this.telefonoProveedor = telefonoProveedor;
    }

    public Integer getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(Integer idRubro) {
        this.idRubro = idRubro;
    }
    
}
