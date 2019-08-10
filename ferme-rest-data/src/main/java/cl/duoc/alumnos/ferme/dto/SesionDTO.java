package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.Sesion;
import cl.duoc.alumnos.ferme.domain.entities.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public class SesionDTO {
    @SuppressWarnings("unused")
	private final static Logger LOG = LoggerFactory.getLogger(SesionDTO.class);
    
    private Integer idSesion;
    private String hashSesion;
    private Integer idUsuario;
    private String nombreUsuario;
    
    private Integer idCargo;
    private Integer idPersona;
    private Integer idCliente;
    private Integer idEmpleado;
    

    public SesionDTO() {
        super();
    }

    public Integer getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(Integer idSesion) {
        this.idSesion = idSesion;
    }

    public String getHashSesion() {
        return hashSesion;
    }

    public void setHashSesion(String hashSesion) {
        this.hashSesion = hashSesion;
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

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

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

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    
    public Sesion toEntity() {
        Sesion entity = new Sesion();
        if (idSesion != null && idSesion != 0) {
            entity.setId(idSesion);
        }
        entity.setHash(hashSesion);
        
        Usuario usuarioEntity = new Usuario();
        usuarioEntity.setId(idUsuario);
        entity.setUsuario(usuarioEntity);
        
        return entity;
    }
    
}
