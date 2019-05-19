package cl.duoc.alumnos.ferme.controllers;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.dto.ProveedorDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IProveedoresService;
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
public class ProveedoresController {
    private final static Logger LOG = LoggerFactory.getLogger(ProveedoresController.class);
    
    @Autowired private IProveedoresService proveedorSvc;
    
    @GetMapping({"/proveedores", "/proveedores/"})
    public Collection<ProveedorDTO> getProveedores(@RequestParam Map<String,String> allRequestParams) {
        
        return this.getProveedores(null, null, allRequestParams);
    }
    
    @GetMapping("/proveedores/{pageSize}")
    public Collection<ProveedorDTO> getProveedores(
            @PathVariable Integer pageSize,
            @RequestParam Map<String,String> allRequestParams
    ) {
        
        return this.getProveedores(pageSize, null, allRequestParams);
    }
    
    /**
     * Busca todos los proveedores. 
     * Si el URL tenía un query string (RequestParam, lo transforma a un Map, genera un Predicate a partir
     * de él y filtra la búsqueda con este Predicate.
     * @param pageSize
     * @param pageIndex
     * @param allRequestParams Un Map conteniendo una colección pares nombre/valor.
     * @see RequestParam
     * @see Predicate
     * @return Una colección de objetos ProveedorDTO
     */
    @GetMapping("/proveedores/{pageSize}/{pageIndex}")
    public Collection<ProveedorDTO> getProveedores(
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
            filtros = this.proveedorSvc.queryParamsMapToProveedoresFilteringPredicate(allRequestParams);
        }
        
        LOG.debug("getProductos - finalPageSize="+finalPageSize+"; finalPageIndex="+finalPageIndex+"; filtros="+filtros);
        return this.proveedorSvc.getProveedores(finalPageSize, finalPageIndex, filtros);
    }
    
    /**
     * Almacena un Rubro nuevo o actualiza uno existente.
     * @param dto Un objeto DTO representando el Rubro a almacenar/actualizar.
     * @return El ID del rubro.
     */
    @PostMapping({"/proveedores/guardar", "/proveedores/guardar/"})
    public Integer saveProveedor(@RequestBody ProveedorDTO dto) {
        
        if (dto != null) {
            LOG.debug("saveProveedor - dto="+dto);
            Integer proveedorId = proveedorSvc.saveProveedor(dto);
            LOG.debug("saveProveedor - proveedorId="+proveedorId);
            return proveedorId;
        }
        return null;
    }
    
    /**
     * Elimina un Rubro de la base de datos.
     * @param proveedorId El ID del Rubro a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue.
     */
    @PostMapping({"/proveedores/borrar", "/proveedores/borrar/"})
    public boolean deleteProveedor(@RequestParam("id") Integer proveedorId) {
        
        if (proveedorId != null && proveedorId != 0) {
            LOG.debug("deleteProveedor - clienteId="+proveedorId);
            return proveedorSvc.deleteProveedor(proveedorId);
        }
        return false;
    }
}
