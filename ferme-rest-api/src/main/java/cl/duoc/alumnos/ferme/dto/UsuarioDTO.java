package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.Persona;
import cl.duoc.alumnos.ferme.domain.entities.Usuario;
import cl.duoc.alumnos.ferme.util.FermeDates;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Benjamin Guillermo
 */
public class UsuarioDTO extends PersonaDTO {
    private final static Logger LOG = LoggerFactory.getLogger(UsuarioDTO.class);
    
    private Integer idUsuario;
    private String nombreUsuario;
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
        try {
            if (idUsuario != 0) {
                entity.setId(idUsuario);
            }
        } catch (NullPointerException exc) {
            LOG.info("toEntity() - idUsuario es null");
        }
        
        entity.setNombre(nombreUsuario);
        
        Date _fechaCreacion = FermeDates.fechaStringToDate(fechaCreacionUsuario);
        if (_fechaCreacion != null) {
            entity.setFechaCreacion(_fechaCreacion);
        }
        
        Persona personaEntity = super.personaToEntity();
        entity.setPersona(personaEntity);
        
        return entity;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" + "idUsuario=" + idUsuario + ", nombreUsuario=" + nombreUsuario + ", fechaCreacionUsuario=" + fechaCreacionUsuario + ", persona=" + super.toString() + '}';
    }
    
}
