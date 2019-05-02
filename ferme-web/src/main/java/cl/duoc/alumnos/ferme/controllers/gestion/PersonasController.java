package cl.duoc.alumnos.ferme.controllers.gestion;

import cl.duoc.alumnos.ferme.FermeParams;
import cl.duoc.alumnos.ferme.dto.ClienteDTO;
import cl.duoc.alumnos.ferme.services.IClientesService;
import cl.duoc.alumnos.ferme.services.IEmpleadosService;
import cl.duoc.alumnos.ferme.services.IProveedoresService;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Benjamin Guillermo
 */
@RestController
@RequestMapping("/api/gestion")
public class PersonasController {
    
    @Autowired private IClientesService clienteSvc;
    //@Autowired private IEmpleadosService empleadoSvc;
    //@Autowired private IProveedoresService proveedorSvc;
    
    
    
    @GetMapping({"/clientes", "/clientes/"})
    public Collection<ClienteDTO> getClientes(@RequestParam Map<String,String> allRequestParams) {
        
        return this.getClientes(null, null, allRequestParams);
    }
    
    @GetMapping("/clientes/{pageSize}")
    public Collection<ClienteDTO> getClientes(
            @PathVariable Integer pageSize,
            @RequestParam Map<String,String> allRequestParams
    ) {
        
        return this.getClientes(pageSize, null, allRequestParams);
    }
    
    /**
     * Busca todos los clientes. 
     * Si el URL tenía un query string (RequestParam, lo transforma a un Map, genera un Predicate a partir
     * de él y filtra la búsqueda con este Predicate.
     * @param pageSize
     * @param pageIndex
     * @param allRequestParams Un Map conteniendo una colección pares nombre/valor.
     * @see RequestParam
     * @see Predicate
     * @return Una colección de objetos ClienteDTO
     */
    @GetMapping("/clientes/{pageSize}/{pageIndex}")
    public Collection<ClienteDTO> getClientes(
        @PathVariable Integer pageSize,
        @PathVariable Integer pageIndex,
        @RequestParam Map<String,String> allRequestParams) {
        
        Integer finalPageSize = FermeParams.DEFAULT_PAGE_SIZE;
        Integer finalPageIndex = FermeParams.DEFAULT_PAGE_INDEX;
        Predicate filtros = null;
        
        if (pageSize != null && pageSize > 0) {
            finalPageSize = pageSize;
        }
        if (pageIndex != null && pageIndex > 0) {
            finalPageIndex = pageIndex-1;
        }
        if (allRequestParams != null && !allRequestParams.isEmpty()) {
            filtros = this.clienteSvc.queryParamsMapToClientesFilteringPredicate(allRequestParams);
        }
        return this.clienteSvc.getClientes(finalPageSize, finalPageIndex, filtros);
    }
}
