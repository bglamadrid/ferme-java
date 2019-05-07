package cl.duoc.alumnos.ferme.services.interfaces;

import cl.duoc.alumnos.ferme.domain.entities.Cliente;
import cl.duoc.alumnos.ferme.dto.ClienteDTO;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
public interface IClientesService {
    
    public Cliente clienteDTOToEntity(ClienteDTO dto);
    public ClienteDTO clienteEntityToDTO(Cliente entity);
    
    /**
     * Obtiene una página (colección) de clientes, con un tamaño determinado 
     * (pudiendo filtrarlos) y los transforma a objetos DTO.
     * @param pageSize El número de resultados que la página mostrará.
     * @param pageIndex El número de página (la primera es 0).
     * @param condicion El objeto Predicate con los filtros a aplicar. Nulable.
     * @return Una colección de objetos DTO.
     */
    public Collection<ClienteDTO> getClientes(int pageSize, int pageIndex, Predicate condicion);
    
    /**
     * Genera una o varias condiciones como un objeto Predicate para filtrar 
     * clientes, tomando un Map de parámetros como base.
     * @param queryParamsMap Un Map de parámetros (presumiblemente proveído por 
     * un query string).
     * @return Un objeto Predicate representando un conjunto de filtros.
     */
    public Predicate queryParamsMapToClientesFilteringPredicate(Map<String,String> queryParamsMap);
    
    /**
     * 
     * Guarda (inserta o actualiza) el cliente.
     * @param dto El objeto DTO de cliente con la información respectiva a guardar.
     * @return El ID del registro guardado.
     */
    public int saveCliente(ClienteDTO dto);
    
    /**
     * 
     * Elimina el registro (y la información) del cliente especificado.
     * @param clienteId El ID del cliente a eliminar.
     * @return true si es exitoso, false si falla.
     */
    public boolean deleteCliente(Integer clienteId);
    
}
