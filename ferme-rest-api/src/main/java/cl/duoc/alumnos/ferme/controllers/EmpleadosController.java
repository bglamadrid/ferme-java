package cl.duoc.alumnos.ferme.controllers;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.EmpleadoDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IEmpleadosService;
import javassist.NotFoundException;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@RestController
@RequestMapping(FermeConfig.URI_BASE_REST_API+"/gestion/empleados")
public class EmpleadosController {
    private final static Logger LOG = LoggerFactory.getLogger(EmpleadosController.class);
    
    @Autowired private IEmpleadosService empleadoSvc;    
    
    @GetMapping("")
    public Collection<EmpleadoDTO> obtener(
        @RequestParam Map<String,String> allRequestParams
    ) {
        LOG.info("obtener sin pagina ni cantidad determinada");
        return this.obtener(null, null, allRequestParams);
    }
    
    @GetMapping("/{pageSize}")
    public Collection<EmpleadoDTO> obtener(
        @PathVariable Integer pageSize,
        @RequestParam Map<String,String> allRequestParams
    ) {
        LOG.info("obtener sin pagina determinada");
        return this.obtener(pageSize, null, allRequestParams);
    }
    
    /**
     * Busca todos los empleados. 
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
    public Collection<EmpleadoDTO> obtener(
        @PathVariable Integer pageSize,
        @PathVariable Integer pageIndex,
        @RequestParam Map<String,String> allRequestParams
    ) {
        LOG.info("obtener");
        
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
            filtros = this.empleadoSvc.queryParamsMapToEmpleadosFilteringPredicate(allRequestParams);
        }
        
        LOG.info("obtener - "+finalPageSize+" registros; página "+finalPageIndex);
        LOG.debug("obtener - Filtros solicitados: "+filtros);
        Collection<EmpleadoDTO> empleados = this.empleadoSvc.getEmpleados(finalPageSize, finalPageIndex, filtros);
        LOG.debug("obtener - empleados.size()="+empleados.size());
        LOG.info("obtener - Solicitud completa. Enviando respuesta al cliente.");
        return empleados;
    }
    
    /**
     * Almacena un Rubro nuevo o actualiza uno existente.
     * @param dto Un objeto DTO representando el Rubro a almacenar/actualizar.
     * @return El ID del rubro.
     * @throws javassist.NotFoundException
     */
    @PostMapping("/guardar")
    public Integer guardar(
        @RequestBody EmpleadoDTO dto
    ) throws NotFoundException {
        LOG.info("guardar");
        if (dto != null) {
            LOG.debug("guardar - dto="+dto);
            Integer empleadoId = empleadoSvc.saveEmpleado(dto);
            LOG.debug("guardar - empleadoId="+empleadoId);
            return empleadoId;
        }
        return null;
    }
    
    /**
     * Elimina un Rubro de la base de datos.
     * @param empleadoId El ID del Rubro a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue.
     */
    @PostMapping("/borrar")
    public boolean borrar(
        @RequestBody Integer empleadoId
    ) {
        LOG.info("borrar");
        if (empleadoId != null && empleadoId != 0) {
            LOG.debug("borrar - empleadoId="+empleadoId);
            return empleadoSvc.deleteEmpleado(empleadoId);
        }
        return false;
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exc) {
    	return new ResponseEntity<>(exc.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
