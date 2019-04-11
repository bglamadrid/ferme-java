package cl.duoc.alumnos.ferme.dto;

/**
 *
 * @author Benjamin Guillermo
 */
public class ClienteDTO {

    private Integer idPersona;
    private Integer idCliente;
    private String direccion;
    private String email;
    private Long fono1;
    private Long fono2;
    private Long fono3;

    public ClienteDTO() {}

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getFono1() {
        return fono1;
    }

    public void setFono1(Long fono1) {
        this.fono1 = fono1;
    }

    public Long getFono2() {
        return fono2;
    }

    public void setFono2(Long fono2) {
        this.fono2 = fono2;
    }

    public Long getFono3() {
        return fono3;
    }

    public void setFono3(Long fono3) {
        this.fono3 = fono3;
    }
    
}
