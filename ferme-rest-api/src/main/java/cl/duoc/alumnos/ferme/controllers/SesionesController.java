package cl.duoc.alumnos.ferme.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.SesionDTO;
import cl.duoc.alumnos.ferme.dto.UsuarioDTO;
import cl.duoc.alumnos.ferme.pojo.LoginPOJO;
import cl.duoc.alumnos.ferme.services.interfaces.ISesionesService;
import cl.duoc.alumnos.ferme.services.interfaces.IUsuariosService;
import javassist.NotFoundException;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@RestController
@RequestMapping(FermeConfig.URI_BASE_REST_API+"/sesiones")
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
    public ResponseEntity<?> abrirSesion(
        @RequestBody LoginPOJO login
    ) throws NotFoundException {
        LOG.info("abrirSesion");
        if (login != null) {
            LOG.debug("abrirSesion - login="+login);
            LOG.info("abrirSesion - Autenticando usuario desde credenciales...");
            UsuarioDTO usuario = usuarioSvc.getUsuarioFromCredentials(login.usuario, login.clave);
            if (usuario != null) {
                LOG.info("abrirSesion - Las credenciales del usuario fueron autenticadas, abriendo sesion...");
                SesionDTO sesion = sesionSvc.abrirSesion(usuario);
                LOG.info("abrirSesion - Transaccion completada correctamente");
                return ResponseEntity.ok(sesion);
            } else {
                LOG.info("abrirSesion - Las credenciales ingresadas son incorrectas");
                return ResponseEntity.noContent().build();
            }
        }
        LOG.info("abrirSesion - Las credenciales ingresadas no son validas");
        return ResponseEntity.badRequest().body(null);
    }
    
    /**
     * Almacena una Sesion nueva o actualiza una existente.
     * @param dto Un objeto DTO representando la Sesion a almacenar/actualizar.
     * @return El ID de la sesion.
     */
    @PostMapping("/validar")
    public boolean validarSesion(
        @RequestBody SesionDTO dto
    ) {
        LOG.info("validarSesion");
        if (dto != null) {
            LOG.debug("validarSesion - dto="+dto);
            return sesionSvc.validarSesion(dto);
        }
        LOG.info("validarSesion - La sesion ingresada ha caducado o no es valida");
        return false;
    }
    
    /**
     * Elimina una Sesion de la base de datos.
     * @param dto El DTO de la Sesion a cerrar.
     * @return Siempre devuelve true, por motivos de seguridad.
     */
    @PostMapping("/cerrar")
    public boolean cerrarSesion(
        @RequestBody SesionDTO dto
    ) {
        LOG.info("cerrarSesion");
        if (dto != null) {
            LOG.info("cerrarSesion - dto="+dto);
            sesionSvc.cerrarSesiones(dto);
        }
        return true;
    }
}
