package cl.duoc.alumnos.ferme.controllers;

import cl.duoc.alumnos.ferme.dto.SesionDTO;
import cl.duoc.alumnos.ferme.dto.UsuarioDTO;
import cl.duoc.alumnos.ferme.pojo.LoginPOJO;
import cl.duoc.alumnos.ferme.services.interfaces.ISesionesService;
import cl.duoc.alumnos.ferme.services.interfaces.IUsuariosService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@RestController
@RequestMapping("/api/sesiones")
public class SesionesController {
    private final static Logger LOG = LoggerFactory.getLogger(SesionesController.class);
    
    @Autowired private ISesionesService sesionSvc;
    @Autowired private IUsuariosService usuarioSvc;
    
    /**
     * Almacena una Sesion nueva o actualiza una existente.
     * @param login Un JSON parseado a un objeto DTO que contiene las credenciales a almacenar/actualizar.
     * @return El ID de la sesion.
     * @throws NotFoundException Si algún ID referenciado no existe en la base de datos
     */
    @PostMapping("/abrir")
    public ResponseEntity<?> abrirSesion(@RequestBody LoginPOJO login) throws NotFoundException {
        
        if (login != null) {
            LOG.debug("abrirSesion - login="+login);
            UsuarioDTO usuario = usuarioSvc.getUsuarioFromCredentials(login.usuario, login.clave);
            if (usuario != null) {
                SesionDTO sesion = sesionSvc.abrirSesion(usuario);
                LOG.debug("abrirSesion - sesionId="+sesion.getIdSesion());
                return ResponseEntity.ok(sesion);
            } else {
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.badRequest().body(null);
    }
    
    /**
     * Almacena una Sesion nueva o actualiza una existente.
     * @param dto Un objeto DTO representando la Sesion a almacenar/actualizar.
     * @return El ID de la sesion.
     */
    @PostMapping("/validar")
    public boolean validarSesion(@RequestBody SesionDTO dto) throws NotFoundException {
        LOG.info("validarSesion");
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
    @PostMapping("/cerrar")
    public boolean cerrarSesion(@RequestBody SesionDTO sesion) {
        
        if (sesion != null && sesion.getIdSesion() != 0) {
            return sesionSvc.cerrarSesion(sesion);
        }
        return false;
    }
}
