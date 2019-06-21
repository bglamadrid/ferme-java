package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.domain.entities.Sesion;
import cl.duoc.alumnos.ferme.domain.entities.Usuario;
import cl.duoc.alumnos.ferme.util.FermeDates;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Benjamin Guillermo
 */
public class SesionDTO {
    private final static Logger LOG = LoggerFactory.getLogger(SesionDTO.class);
    
    private Integer idSesion;
    private String abiertaSesion;
    private String vigenteSesion;
    private String hashSesion;
    private String cerradaSesion;
    private Integer idUsuario;
    private String nombreUsuario;
    

    public SesionDTO() {
        super();
    }

    public Integer getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(Integer idSesion) {
        this.idSesion = idSesion;
    }

    public String getAbiertaSesion() {
        return abiertaSesion;
    }

    public void setAbiertaSesion(String abiertaSesion) {
        this.abiertaSesion = abiertaSesion;
    }

    public String getVigenteSesion() {
        return vigenteSesion;
    }

    public void setVigenteSesion(String vigenteSesion) {
        this.vigenteSesion = vigenteSesion;
    }

    public String getHashSesion() {
        return hashSesion;
    }

    public void setHashSesion(String hashSesion) {
        this.hashSesion = hashSesion;
    }

    public String getCerradaSesion() {
        return cerradaSesion;
    }

    public void setCerradaSesion(String cerradaSesion) {
        this.cerradaSesion = cerradaSesion;
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
    
    public Sesion toEntity() {
        Sesion entity = new Sesion();
        if (idSesion != null && idSesion != 0) {
            entity.setId(idSesion);
        }
        entity.setVigente(vigenteSesion);
        entity.setHash(hashSesion);
        
        Date fechaAbierta = FermeDates.fechaStringToDate(abiertaSesion);
        entity.setAbierta(fechaAbierta);
        
        if (cerradaSesion != null && !cerradaSesion.isEmpty()) {
            Date fechaCerrada = FermeDates.fechaStringToDate(cerradaSesion);
            entity.setCerrada(fechaCerrada);
        }
        
        Usuario usuarioEntity = new Usuario();
        usuarioEntity.setId(idUsuario);
        entity.setUsuario(usuarioEntity);
        
        return entity;
    }
    
}
