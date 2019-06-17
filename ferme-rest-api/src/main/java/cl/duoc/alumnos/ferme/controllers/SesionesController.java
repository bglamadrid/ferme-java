package cl.duoc.alumnos.ferme.controllers;

import cl.duoc.alumnos.ferme.dto.SesionDTO;
import cl.duoc.alumnos.ferme.dto.UsuarioDTO;
import cl.duoc.alumnos.ferme.services.interfaces.ISesionesService;
import cl.duoc.alumnos.ferme.services.interfaces.IUsuariosService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

class Login {
    public String usuario;
    public String clave;
}

/**
 *
 * @author Benjamin Guillermo
 */
@RestController
@RequestMapping("/api/gestion")
public class SesionesController {
    private final static Logger LOG = LoggerFactory.getLogger(SesionesController.class);
    
    @Autowired private ISesionesService sesionSvc;
    @Autowired private IUsuariosService usuarioSvc;
    
    /**
     * Almacena una Sesion nueva o actualiza una existente.
     * @param login Un JSON parseado a un objeto DTO que contiene las credenciales a almacenar/actualizar.
     * @return El ID de la sesion.
     */
    @PostMapping("/sesiones/abrir")
    public SesionDTO abrirSesion(@RequestBody Login login) throws NotFoundException {
        
        if (login != null) {
            LOG.debug("abrirSesion - login="+login);
            UsuarioDTO usuario = usuarioSvc.getUsuarioFromCredentials(login.usuario, login.clave);
            SesionDTO sesion = sesionSvc.abrirSesion(usuario);
            LOG.debug("abrirSesion - sesionId="+sesion.getIdSesion());
            return sesion;
        }
        return null;
    }
    
    /**
     * Almacena una Sesion nueva o actualiza una existente.
     * @param dto Un objeto DTO representando la Sesion a almacenar/actualizar.
     * @return El ID de la sesion.
     */
    @PostMapping("/sesiones/validar")
    public boolean validarSesion(@RequestBody SesionDTO dto) throws NotFoundException {
        
        if (dto != null) {
            return sesionSvc.validarSesion(dto);
        }
        return false;
    }
    
    /**
     * Elimina una Sesion de la base de datos.
     * @param sesion El DTO de la Sesion a cerrar.
     * @return true si la operación fue exitosa, false si no lo fue.
     */
    @PostMapping("/sesiones/cerrar")
    public boolean cerrarSesion(@RequestBody SesionDTO sesion) {
        
        if (sesion != null && sesion.getIdSesion() != 0) {
            return sesionSvc.cerrarSesion(sesion);
        }
        return false;
    }
}
