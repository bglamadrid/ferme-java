package cl.duoc.alumnos.ferme.controllers;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.dto.EmpleadoDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IEmpleadosService;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;
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
 * @author Benjamin Guillermo
 */
@RestController
@RequestMapping("/api/gestion")
public class EmpleadosController {
    private final static Logger LOG = LoggerFactory.getLogger(EmpleadosController.class);
    
    @Autowired private IEmpleadosService empleadoSvc;    
    
    @GetMapping({"/empleados", "/empleados/"})
    public Collection<EmpleadoDTO> getEmpleados(@RequestParam Map<String,String> allRequestParams) {
        
        return this.getEmpleados(null, null, allRequestParams);
    }
    
    @GetMapping("/empleados/{pageSize}")
    public Collection<EmpleadoDTO> getEmpleados(
            @PathVariable Integer pageSize,
            @RequestParam Map<String,String> allRequestParams
    ) {
        
        return this.getEmpleados(pageSize, null, allRequestParams);
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
    @GetMapping("/empleados/{pageSize}/{pageIndex}")
    public Collection<EmpleadoDTO> getEmpleados(
        @PathVariable Integer pageSize,
        @PathVariable Integer pageIndex,
        @RequestParam Map<String,String> allRequestParams) {
        
        Integer finalPageSize = Ferme.DEFAULT_PAGE_SIZE;
        Integer finalPageIndex = Ferme.DEFAULT_PAGE_INDEX;
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
        
        LOG.debug("getEmpleados - finalPageSize="+finalPageSize+"; finalPageIndex="+finalPageIndex+"; filtros="+filtros);
        return this.empleadoSvc.getEmpleados(finalPageSize, finalPageIndex, filtros);
    }
    
    /**
     * Almacena un Rubro nuevo o actualiza uno existente.
     * @param dto Un objeto DTO representando el Rubro a almacenar/actualizar.
     * @return El ID del rubro.
     */
    @PostMapping({"/empleados/guardar", "/empleados/guardar/"})
    public Integer saveEmpleado(@RequestBody EmpleadoDTO dto) {
        
        if (dto != null) {
            LOG.debug("saveEmpleado - dto="+dto);
            Integer empleadoId = empleadoSvc.saveEmpleado(dto);
            LOG.debug("saveEmpleado - empleadoId="+empleadoId);
            return empleadoId;
        }
        return null;
    }
    
    /**
     * Elimina un Rubro de la base de datos.
     * @param empleadoId El ID del Rubro a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue.
     */
    @PostMapping({"/empleados/borrar", "/empleados/borrar/"})
    public boolean deleteEmpleado(@RequestParam("id") Integer empleadoId) {
        
        if (empleadoId != null && empleadoId != 0) {
            LOG.debug("deleteEmpleado - empleadoId="+empleadoId);
            return empleadoSvc.deleteEmpleado(empleadoId);
        }
        return false;
    }
}
