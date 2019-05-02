package cl.duoc.alumnos.ferme.services.imp;

import cl.duoc.alumnos.ferme.domain.entities.Cliente;
import cl.duoc.alumnos.ferme.domain.entities.QCliente;
import cl.duoc.alumnos.ferme.domain.repositories.IClientesRepository;
import cl.duoc.alumnos.ferme.dto.ClienteDTO;
import com.querydsl.core.types.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.duoc.alumnos.ferme.services.IClientesService;
import com.querydsl.core.BooleanBuilder;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author got12
 */
@Service
public class ClientesService implements IClientesService {
    
    @Autowired IClientesRepository clienteRepo;
    @Autowired EntityManager em;
    private final static Logger LOG = LoggerFactory.getLogger(ProductosService.class);

    @Override
    public Cliente clienteDTOToEntity(ClienteDTO dto) {
        Cliente entity = new Cliente();
        
        if (dto.getIdCliente()!= null && dto.getIdCliente() != 0) {
            entity.setId(dto.getIdCliente());
        }
        entity.setDireccion(dto.getDireccionCliente());
        entity.setEmail(dto.getEmailCliente());
        if (dto.getFonoCliente1() != null && dto.getFonoCliente1() != 0) {
            entity.setFono1(dto.getFonoCliente1());
        }
        if (dto.getFonoCliente2() != null && dto.getFonoCliente2() != 0) {
            entity.setFono2(dto.getFonoCliente2());
        }
        if (dto.getFonoCliente3() != null && dto.getFonoCliente3() != 0) {
            entity.setFono3(dto.getFonoCliente3());
        }
        
        return entity;
    }

    @Override
    public ClienteDTO clienteEntityToDTO(Cliente entity) {
        ClienteDTO dto = new ClienteDTO();
        
        dto.setIdCliente(entity.getId());
        dto.setDireccionCliente(entity.getDireccion());
        
        return dto;
    }

    @Override
    public Collection<ClienteDTO> getClientes(int pageSize, int pageIndex, Predicate condicion) {
        Pageable pgbl = PageRequest.of(pageIndex, pageSize);
        
        List<ClienteDTO> pagina = new ArrayList<>();
        Iterable<Cliente> clientes;
        
        if (condicion == null) {
            clientes = clienteRepo.findAll(pgbl);
        } else {
            clientes = clienteRepo.findAll(condicion, pgbl);
        }
        
        clientes.forEach((entity) -> {
            ClienteDTO dto = this.clienteEntityToDTO(entity);
            pagina.add(dto);
        });
        
        return pagina;
    }

    @Override
    public Predicate queryParamsMapToClientesFilteringPredicate(Map<String, String> queryParamsMap) {
        
        QCliente qCliente = QCliente.cliente;
        BooleanBuilder bb = new BooleanBuilder();
        for (String paramName : queryParamsMap.keySet()) {
            String paramValue = queryParamsMap.get(paramName);
            LOG.debug(paramName+"="+paramValue);
            try {
                Integer parsedValueI;
                switch (paramName) {
                    case "id":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qCliente.id.eq(parsedValueI));
                        return bb; //match por id es único
                    case "direccion":
                        paramValue = "%" + paramValue.toUpperCase() + "%";
                        bb.and(qCliente.direccion.upper().like(paramValue));
                        break;
                    default: break;
                }
            } catch (NumberFormatException exc) {
                LOG.error("No se pudo traducir el parámetro '" + paramName + "' a un número (su valor era '" + paramValue + "').", exc);
            }
        }
        
        return bb;
    }

    @Override
    public int saveCliente(ClienteDTO dto) {
        
        Cliente entity = this.clienteDTOToEntity(dto);
        entity = clienteRepo.saveAndFlush(entity);
        return entity.getId();
    }

    @Override
    public boolean deleteCliente(Integer cargoId) {
        
        try {
            clienteRepo.deleteById(cargoId);
            return true;
        } catch (IllegalArgumentException exc) {
            LOG.error("Error al borrar Cliente con id " +cargoId, exc);
        }
        return false;
    }

    
    
}
