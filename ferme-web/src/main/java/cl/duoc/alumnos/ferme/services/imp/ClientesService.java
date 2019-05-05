package cl.duoc.alumnos.ferme.services.imp;

import cl.duoc.alumnos.ferme.domain.entities.Cliente;
import cl.duoc.alumnos.ferme.domain.entities.Persona;
import cl.duoc.alumnos.ferme.domain.entities.QCliente;
import cl.duoc.alumnos.ferme.domain.repositories.IClientesRepository;
import cl.duoc.alumnos.ferme.dto.ClienteDTO;
import cl.duoc.alumnos.ferme.dto.PersonaDTO;
import cl.duoc.alumnos.ferme.services.IClientesService;
import cl.duoc.alumnos.ferme.services.IPersonasService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author got12
 */
@Service
public class ClientesService implements IClientesService {
    
    @Autowired IPersonasService personaSvc;
    @Autowired IClientesRepository clienteRepo;
    @Autowired EntityManager em;
    private final static Logger LOG = LoggerFactory.getLogger(ProductosService.class);

    @Override
    public Cliente clienteDTOToEntity(ClienteDTO dto) throws NullPointerException {
        Cliente entity = new Cliente();
        
        // Persona es la representación de la información personal de un Cliente
        PersonaDTO persona = dto.getPersona(); 
        if (persona == null) {
            throw new NullPointerException("El Cliente recibido posee una Persona con valor null.");
        } else {
        
            if (dto.getIdCliente()!= null && dto.getIdCliente() != 0) {
                entity.setId(dto.getIdCliente());
            }
            
            Persona personaEntity;
            if (persona.getIdPersona() == null || persona.getIdPersona() == 0) { // si la persona es 'nueva', sólo hace conversión a Entity
                personaEntity = personaSvc.personaDTOToEntity(persona);
            } else { // de lo contrario, buscamos al Cliente en base a la Persona
                BooleanExpression wherePersonaIdIsThisPersonasId = QCliente.cliente.persona.id.eq(persona.getIdPersona());
                try {
                    Optional<Cliente> realCliente = clienteRepo.findOne(wherePersonaIdIsThisPersonasId);
                    personaEntity = realCliente.get().getPersona();
                } catch (NoSuchElementException exc) {
                    personaEntity = personaSvc.personaDTOToEntity(persona);
                    LOG.info("No se encontró un Cliente asociado a Persona[ idPersona="+persona.getIdPersona()+"], se realizó una conversión manual.", exc);
                } catch (IncorrectResultSizeDataAccessException exc) {
                    List<Cliente> realClientes = new ArrayList<>();
                    clienteRepo.findAll(wherePersonaIdIsThisPersonasId).forEach(realClientes::add);
                    entity = realClientes.get(realClientes.size()-1);
                    personaEntity = entity.getPersona();
                    LOG.info("Muchos Cliente asociados a Persona[ idPersona="+persona.getIdPersona()+"], el Cliente elegido es el último creado ", exc);
                }
            }
            personaEntity.setNombreCompleto(persona.getNombreCompletoPersona());
            personaEntity.setRut(persona.getRutPersona());
            
            entity.setPersona(personaEntity);
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
        dto.setEmailCliente(entity.getEmail());
        dto.setFonoCliente1(entity.getFono1());
        dto.setFonoCliente2(entity.getFono2());
        dto.setFonoCliente3(entity.getFono3());
        
        Persona entityPersona = entity.getPersona();
        PersonaDTO persona = personaSvc.personaEntityToDTO(entityPersona);
        dto.setPersona(persona);
        
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
