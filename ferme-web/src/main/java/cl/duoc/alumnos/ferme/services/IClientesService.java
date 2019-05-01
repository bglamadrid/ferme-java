package cl.duoc.alumnos.ferme.services;

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
    
    public Collection<ClienteDTO> getClientes();
    
    public Predicate queryParamsMapToClientesFilteringPredicate(Map<String,String> queryParamsMap);
    
}
