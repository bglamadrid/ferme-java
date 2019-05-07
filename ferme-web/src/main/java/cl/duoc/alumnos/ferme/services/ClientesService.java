package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.domain.entities.Cliente;
import cl.duoc.alumnos.ferme.domain.entities.Persona;
import cl.duoc.alumnos.ferme.domain.entities.QCliente;
import cl.duoc.alumnos.ferme.domain.repositories.IClientesRepository;
import cl.duoc.alumnos.ferme.dto.ClienteDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IClientesService;
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
import org.springframework.dao.EmptyResultDataAccessException;
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
    
    @Autowired IClientesRepository clienteRepo;
    @Autowired EntityManager em;
    private final static Logger LOG = LoggerFactory.getLogger(ProductosService.class);

    @Override
    public Cliente clienteDTOToEntity(ClienteDTO dto) throws NullPointerException {
        Cliente entity = new Cliente();
        
            if (dto.getIdCliente()!= null && dto.getIdCliente() != 0) {
                entity.setId(dto.getIdCliente());
            }
            
        // Persona es la representación de la información personal de un Cliente
        Integer personaId = dto.getIdPersona(); 
        Persona personaEntity;
        if (personaId == null || personaId == 0) { // si la persona es 'nueva', sólo reamos manualmente el Entity
            personaEntity = new Persona();
        } else { // de lo contrario, buscamos al Cliente en base a la Persona
            BooleanExpression wherePersonaIdIsThisPersonasId = QCliente.cliente.persona.id.eq(personaId);
            try {
                Optional<Cliente> realCliente = clienteRepo.findOne(wherePersonaIdIsThisPersonasId);
                personaEntity = realCliente.get().getPersona();
            } catch (NoSuchElementException exc) {
                personaEntity = new Persona();
                LOG.warn("No se encontró un Cliente asociado a Persona[ idPersona="+personaId+"], se realizó una conversión manual.", exc);
            } catch (IncorrectResultSizeDataAccessException exc) {
                List<Cliente> realClientes = new ArrayList<>();
                clienteRepo.findAll(wherePersonaIdIsThisPersonasId).forEach(realClientes::add);
                entity = realClientes.get(realClientes.size()-1);
                personaEntity = entity.getPersona();
                LOG.warn("Muchos Cliente asociados a Persona[ idPersona="+personaId+"], el Cliente elegido es el último creado.", exc);
            }
        }
        
        personaEntity.setNombreCompleto(dto.getNombreCompletoPersona());
        personaEntity.setRut(dto.getRutPersona());
        personaEntity.setDireccion(dto.getDireccionPersona());
        personaEntity.setEmail(dto.getEmailPersona());
        personaEntity.setFono1(dto.getFonoPersona1());
        personaEntity.setFono2(dto.getFonoPersona2());
        personaEntity.setFono3(dto.getFonoPersona3());

        entity.setPersona(personaEntity);
        
        return entity;
    }

    @Override
    public ClienteDTO clienteEntityToDTO(Cliente entity) {
        ClienteDTO dto = new ClienteDTO();
        Persona personaEntity = entity.getPersona();
        
        dto.setIdCliente(entity.getId());
        dto.setNombreCompletoPersona(personaEntity.getNombreCompleto());
        dto.setRutPersona(personaEntity.getRut());
        dto.setDireccionPersona(personaEntity.getDireccion());
        dto.setEmailPersona(personaEntity.getEmail());
        dto.setFonoPersona1(personaEntity.getFono1());
        dto.setFonoPersona2(personaEntity.getFono2());
        dto.setFonoPersona3(personaEntity.getFono3());
        
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
                    case "nombre":
                        paramValue = "%" + paramValue.toUpperCase() + "%";
                        bb.and(qCliente.persona.nombreCompleto.upper().like(paramValue));
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
        } catch (EmptyResultDataAccessException exc) {
            LOG.error("Error al borrar Cliente con id " +cargoId, exc);
        }
        return false;
    }

    
    
}
