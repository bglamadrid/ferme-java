package cl.duoc.alumnos.ferme.dto;

/**
 *
 * @author Benjamin Guillermo
 */
public class ClienteDTO {

    private Integer idPersona;
    private Integer idCliente;
    private String direccionCliente;
    private String emailCliente;
    private Long fonoCliente1;
    private Long fonoCliente2;
    private Long fonoCliente3;

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

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public Long getFonoCliente1() {
        return fonoCliente1;
    }

    public void setFonoCliente1(Long fonoCliente1) {
        this.fonoCliente1 = fonoCliente1;
    }

    public Long getFonoCliente2() {
        return fonoCliente2;
    }

    public void setFonoCliente2(Long fonoCliente2) {
        this.fonoCliente2 = fonoCliente2;
    }

    public Long getFonoCliente3() {
        return fonoCliente3;
    }

    public void setFonoCliente3(Long fonoCliente3) {
        this.fonoCliente3 = fonoCliente3;
    }
    
}
