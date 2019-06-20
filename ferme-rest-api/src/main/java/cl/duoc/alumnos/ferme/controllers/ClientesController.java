package cl.duoc.alumnos.ferme.controllers;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.dto.ClienteDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IClientesService;
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
public class ClientesController {
    private final static Logger LOG = LoggerFactory.getLogger(ClientesController.class);
    
    @Autowired private IClientesService clienteSvc;    
    
    @GetMapping("/clientes")
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
        
        LOG.info("getClientes - "+finalPageSize+" registros; página "+finalPageIndex);
        LOG.debug("getClientes - Filtros solicitados: "+filtros);
        Collection<ClienteDTO> clientes = this.clienteSvc.getClientes(finalPageSize, finalPageIndex, filtros);
        LOG.debug("getClientes - clientes.size()="+clientes.size());
        LOG.info("getClientes - Solicitud completa. Enviando respuesta al cliente.");
        return clientes;
    }
    
    /**
     * Almacena un Cliente nuevo o actualiza uno existente.
     * @param dto Un ClienteDTO representando el Cliente a almacenar/actualizar.
     * @return El ID del cliente, 0 si falla al insertar, null si el JSON viene incorrecto.
     */
    @PostMapping("/clientes/guardar")
    public Integer saveCliente(@RequestBody ClienteDTO dto) {
        if (dto != null) {
            LOG.debug("saveCliente - dto="+dto);
            Integer clienteId = clienteSvc.saveCliente(dto);
            LOG.debug("saveCliente - clienteId="+clienteId);
            return clienteId;
        }
        return null;
    }
    
    /**
     * Elimina un Cliente de la base de datos.
     * @param clienteId El ID del Cliente a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue
     */
    @PostMapping("/clientes/borrar")
    public boolean deleteCliente(@RequestBody Integer clienteId) {
        
        if (clienteId != null && clienteId != 0) {
            LOG.debug("deleteCliente - clienteId="+clienteId);
            return clienteSvc.deleteCliente(clienteId);
        }
        return false;
    }
}
