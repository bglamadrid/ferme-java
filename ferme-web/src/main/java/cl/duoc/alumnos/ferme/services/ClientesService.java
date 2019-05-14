package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.domain.entities.Cliente;
import cl.duoc.alumnos.ferme.domain.entities.Persona;
import cl.duoc.alumnos.ferme.domain.entities.QCliente;
import cl.duoc.alumnos.ferme.domain.repositories.IClientesRepository;
import cl.duoc.alumnos.ferme.domain.repositories.IPersonasRepository;
import cl.duoc.alumnos.ferme.dto.ClienteDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IClientesService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author got12
 */
@Service
public class ClientesService implements IClientesService {
    
    @Autowired IClientesRepository clienteRepo;
    @Autowired IPersonasRepository personaRepo;
    private final static Logger LOG = LoggerFactory.getLogger(ProductosService.class);

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
            ClienteDTO dto = entity.toDTO();
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
                    case "nombre":
                        paramValue = "%" + paramValue.toUpperCase() + "%";
                        bb.and(qCliente.persona.nombreCompleto.upper().like(paramValue));
                        break;
                    case "rut":
                        paramValue = "%" + paramValue.toUpperCase() + "%";
                        bb.and(qCliente.persona.rut.upper().like(paramValue));
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
        
        Cliente entity = dto.toEntity();
        Persona personaEntity = entity.getPersona();
        personaEntity = personaRepo.saveAndFlush(personaEntity);
        entity.setPersona(personaEntity);
        entity = clienteRepo.saveAndFlush(entity);
        return entity.getId();
    }

    @Override
    public boolean deleteCliente(Integer cargoId) {
        
        try {
            clienteRepo.deleteById(cargoId);
            return true;
        } catch (EmptyResultDataAccessException exc) {
            LOG.error("Error al borrar Cliente con id " +cargoId, exc);
        }
        return false;
    }

    
    
}
