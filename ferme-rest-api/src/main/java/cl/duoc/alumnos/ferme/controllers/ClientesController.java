package cl.duoc.alumnos.ferme.controllers;

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

import com.querydsl.core.types.Predicate;

import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.ClienteDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IClientesService;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@RestController
@RequestMapping(FermeConfig.URI_BASE_REST_API+"/gestion/clientes")
public class ClientesController {
    private final static Logger LOG = LoggerFactory.getLogger(ClientesController.class);
    
    @Autowired private IClientesService clienteSvc;    
    
    @GetMapping("")
    public Collection<ClienteDTO> obtener(
        @RequestParam Map<String,String> allRequestParams
    ) {
        LOG.info("obtener sin pagina ni cantidad determinada");
        return this.obtener(null, null, allRequestParams);
    }
    
    @GetMapping("/{pageSize}")
    public Collection<ClienteDTO> obtener(
            @PathVariable Integer pageSize,
            @RequestParam Map<String,String> allRequestParams
    ) {
        LOG.info("obtener sin pagina determinada");
        return this.obtener(pageSize, null, allRequestParams);
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
    @GetMapping("/{pageSize}/{pageIndex}")
    public Collection<ClienteDTO> obtener(
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
            filtros = this.clienteSvc.queryParamsMapToClientesFilteringPredicate(allRequestParams);
        }
        
        LOG.info("obtener - "+finalPageSize+" registros; página "+finalPageIndex);
        LOG.debug("obtener - Filtros solicitados: "+filtros);
        Collection<ClienteDTO> clientes = this.clienteSvc.getClientes(finalPageSize, finalPageIndex, filtros);
        LOG.debug("obtener - clientes.size()="+clientes.size());
        LOG.info("obtener - Solicitud completa. Enviando respuesta al cliente.");
        return clientes;
    }
    
    /**
     * Almacena un Cliente nuevo o actualiza uno existente.
     * @param dto Un ClienteDTO representando el Cliente a almacenar/actualizar.
     * @return El ID del cliente, 0 si falla al insertar, null si el JSON viene incorrecto.
     */
    @PostMapping("/guardar")
    public Integer guardar(
        @RequestBody ClienteDTO dto
    ) {
        LOG.info("guardar");
        if (dto != null) {
            LOG.debug("guardar - dto="+dto);
            Integer clienteId = clienteSvc.saveCliente(dto);
            LOG.debug("guardar - clienteId="+clienteId);
            return clienteId;
        }
        return null;
    }
    
    /**
     * Elimina un Cliente de la base de datos.
     * @param clienteId El ID del Cliente a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue
     */
    @PostMapping("/borrar")
    public boolean borrar(
        @RequestBody Integer clienteId
    ) {
        LOG.info("borrar");
        if (clienteId != null && clienteId != 0) {
            LOG.debug("borrar - clienteId="+clienteId);
            return clienteSvc.deleteCliente(clienteId);
        }
        return false;
    }
}
