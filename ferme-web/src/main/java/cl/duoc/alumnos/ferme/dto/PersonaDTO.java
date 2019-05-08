package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.Persona;

/**
 *
 * @author Benjamin Guillermo
 */
public class PersonaDTO {
    
    protected Integer idPersona;
    protected String nombreCompletoPersona;
    protected String rutPersona;
    protected String direccionPersona;
    protected String emailPersona;
    protected Long fonoPersona1;
    protected Long fonoPersona2;
    protected Long fonoPersona3;

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
    
    public Persona personaToEntity() {
        Persona personaEntity = new Persona();
        if (idPersona != null) {
            personaEntity.setId(idPersona);
        }
        
        personaEntity.setNombreCompleto(nombreCompletoPersona);
        personaEntity.setRut(rutPersona);
        
        if (direccionPersona != null) {
            personaEntity.setDireccion(direccionPersona);
        }
        if (emailPersona != null) {
            personaEntity.setEmail(emailPersona);
        }
        if (fonoPersona1 != null) {
            personaEntity.setFono1(fonoPersona1);
        }
        if (fonoPersona2 != null) {
            personaEntity.setFono2(fonoPersona2);
        }
        if (fonoPersona3 != null) {
            personaEntity.setFono3(fonoPersona3);
        }
        
        return personaEntity;
    }
    
    
}
