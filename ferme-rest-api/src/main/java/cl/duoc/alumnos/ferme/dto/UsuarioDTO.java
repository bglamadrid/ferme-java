package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.Persona;
import cl.duoc.alumnos.ferme.domain.entities.Usuario;
import cl.duoc.alumnos.ferme.util.FermeDates;
import java.util.Date;

/**
 *
 * @author Benjamin Guillermo
 */
public class UsuarioDTO extends PersonaDTO {
    
    private Integer idUsuario;
    private String nombreUsuario;
    private String claveUsuario;
    private String fechaCreacionUsuario;
    private String sesion;
    

    public UsuarioDTO() {
        super();
    }

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

    public String getSesion() {
        return sesion;
    }

    public void setSesion(String sesion) {
        this.sesion = sesion;
    }

    public String getFechaCreacionUsuario() {
        return fechaCreacionUsuario;
    }

    public void setFechaCreacionUsuario(String fechaCreacionUsuario) {
        this.fechaCreacionUsuario = fechaCreacionUsuario;
    }
    
    public Usuario toEntity() {
        Usuario entity = new Usuario();
        if (idUsuario != null && idUsuario != 0) {
            entity.setId(idUsuario);
        }
        entity.setNombre(nombreUsuario);
        
        if (claveUsuario != null && !claveUsuario.isEmpty()) {
            entity.setClave(claveUsuario);
        } 
        
        Date _fechaCreacion = FermeDates.fechaStringToDate(fechaCreacionUsuario);
        entity.setFechaCreacion(_fechaCreacion);
        
        Persona personaEntity = super.personaToEntity();
        entity.setPersona(personaEntity);
        
        return entity;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" + "idUsuario=" + idUsuario + ", nombreUsuario=" + nombreUsuario + ", fechaCreacionUsuario=" + fechaCreacionUsuario + ", persona=" + super.toString() + '}';
    }
    
}
