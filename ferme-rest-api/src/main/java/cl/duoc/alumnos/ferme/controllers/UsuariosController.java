package cl.duoc.alumnos.ferme.controllers;

import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.UsuarioDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IUsuariosService;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@RestController
@RequestMapping(FermeConfig.URI_BASE_REST_API+"/gestion/usuarios")
public class UsuariosController {
    private final static Logger LOG = LoggerFactory.getLogger(UsuariosController.class);
    
    @Autowired private IUsuariosService usuarioSvc;
    
    @GetMapping("")
    public Collection<UsuarioDTO> obtener(@RequestParam Map<String,String> allRequestParams) {
        
        return this.obtener(null, null, allRequestParams);
    }
    
    @GetMapping("/{pageSize}")
    public Collection<UsuarioDTO> obtener(
        @PathVariable Integer pageSize,
        @RequestParam Map<String,String> allRequestParams
    ) {
        return this.obtener(pageSize, null, allRequestParams);
    }
    
    /**
     * Busca todos los usuarios. 
     * Si el URL tenía un query string (RequestParam, lo transforma a un Map, genera un Predicate a partir
     * de él y filtra la búsqueda con este Predicate.
     * @param pageSize
     * @param pageIndex
     * @param allRequestParams Un Map conteniendo una colección pares nombre/valor.
     * @see RequestParam
     * @see Predicate
     * @return Una colección de objetos EmpleadoDTO
     */
    @GetMapping("/{pageSize}/{pageIndex}")
    public Collection<UsuarioDTO> obtener(
        @PathVariable Integer pageSize,
        @PathVariable Integer pageIndex,
        @RequestParam Map<String,String> allRequestParams) {
        
        Integer finalPageSize = FermeConfig.PAGINACION_REGISTROS_POR_PAGINA_INICIAL;
        Integer finalPageIndex = FermeConfig.PAGINACION_INDICE_INICIAL;
        Predicate filtros = null;
        
        if (pageSize != null && pageSize > 0) {
            finalPageSize = pageSize;
        }
        if (pageIndex != null && pageIndex > 0) {
            finalPageIndex = pageIndex-1;
        }
        if (allRequestParams != null && !allRequestParams.isEmpty()) {
            filtros = this.usuarioSvc.queryParamsMapToUsuariosFilteringPredicate(allRequestParams);
        }
        
        LOG.info("getUsuarios - "+finalPageSize+" registros; página "+finalPageIndex);
        LOG.debug("getUsuarios - Filtros solicitados: "+filtros);
        Collection<UsuarioDTO> usuarios = this.usuarioSvc.getUsuarios(finalPageSize, finalPageIndex, filtros);
        LOG.debug("getUsuarios - usuarios.size()="+usuarios.size());
        LOG.info("getUsuarios - Solicitud completa. Enviando respuesta al cliente.");
        return usuarios;
    }
    
    /**
     * Almacena un Usuario nuevo o actualiza uno existente.
     * @param dto Un objeto DTO representando el Usuario a almacenar/actualizar.
     * @return El ID del usuario.
     * @throws javassist.NotFoundException
     */
    @PostMapping("/guardar")
    public Integer guardar(@RequestBody UsuarioDTO dto) throws NotFoundException {
        
        if (dto != null) {
            LOG.debug("saveUsuario - dto="+dto);
            Integer usuarioId = usuarioSvc.saveUsuario(dto);
            LOG.debug("saveUsuario - usuarioId="+usuarioId);
            return usuarioId;
        }
        return null;
    }
    
    /**
     * Elimina un Usuario de la base de datos.
     * @param usuarioId El ID del Rubro a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue.
     */
    @PostMapping("/borrar")
    public boolean borrar(@RequestBody Integer usuarioId) {
        
        if (usuarioId != null && usuarioId != 0) {
            LOG.debug("deleteUsuario - usuarioId="+usuarioId);
            return usuarioSvc.deleteUsuario(usuarioId);
        }
        return false;
    }
}
