package cl.duoc.alumnos.ferme.controllers.gestion;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.dto.ClienteDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IClientesService;
import cl.duoc.alumnos.ferme.services.interfaces.IEmpleadosService;
import cl.duoc.alumnos.ferme.services.interfaces.IProveedoresService;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;
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
public class ClientesController {
    
    @Autowired private IClientesService clienteSvc;    
    
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
            filtros = this.clienteSvc.queryParamsMapToClientesFilteringPredicate(allRequestParams);
        }
        return this.clienteSvc.getClientes(finalPageSize, finalPageIndex, filtros);
    }
    
    /**
     * Almacena un Rubro nuevo o actualiza uno existente.
     * @param dto Un objeto DTO representando el Rubro a almacenar/actualizar.
     * @return El ID del rubro.
     */
    @PostMapping({"/clientes/guardar", "/clientes/guardar/"})
    public Integer saveRubro(@RequestBody ClienteDTO dto) {
        
        if (dto != null) {
            return clienteSvc.saveCliente(dto);
        }
        return null;
    }
    
    /**
     * Elimina un Rubro de la base de datos.
     * @param clienteId El ID del Rubro a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue.
     */
    @PostMapping({"/clientes/borrar", "/clientes/borrar/"})
    public boolean deleteRubro(@RequestParam("id") Integer clienteId) {
        
        if (clienteId != null && clienteId != 0) {
            return clienteSvc.deleteCliente(clienteId);
        }
        return false;
    }
}
