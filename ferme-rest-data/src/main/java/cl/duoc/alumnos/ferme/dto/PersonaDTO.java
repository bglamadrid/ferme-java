package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.Persona;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public class PersonaDTO {
    @SuppressWarnings("unused")
	private final static Logger LOG = LoggerFactory.getLogger(PersonaDTO.class);
    
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
        if (idPersona != null && idPersona != 0) {
            personaEntity.setId(idPersona);
        }
        personaEntity.setNombreCompleto(nombreCompletoPersona);
        personaEntity.setRut(rutPersona);
        
        if (direccionPersona != null && !direccionPersona.isEmpty()) {
            personaEntity.setDireccion(direccionPersona);
        }
        if (emailPersona != null && !emailPersona.isEmpty()) {
            personaEntity.setEmail(emailPersona);
        }
        if (fonoPersona1 != null && fonoPersona1 > 0L) {
            personaEntity.setFono1(fonoPersona1);
        }
        if (fonoPersona2 != null && fonoPersona2 > 0L) {
            personaEntity.setFono2(fonoPersona2);
        }
        if (fonoPersona3 != null && fonoPersona3 > 0L) {
            personaEntity.setFono3(fonoPersona3);
        }
        
        return personaEntity;
    }

    @Override
    public String toString() {
        return "PersonaDTO{" + "idPersona=" + idPersona + ", nombreCompletoPersona=" + nombreCompletoPersona + ", rutPersona=" + rutPersona + ", direccionPersona=" + direccionPersona + ", emailPersona=" + emailPersona + ", fonoPersona1=" + fonoPersona1 + ", fonoPersona2=" + fonoPersona2 + ", fonoPersona3=" + fonoPersona3 + '}';
    }
    
}
