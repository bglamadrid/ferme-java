package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.domain.entities.Cliente;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author got12
 */
@Service
@Transactional
public class ClientesService implements IClientesService {
    private final static Logger LOG = LoggerFactory.getLogger(ClientesService.class);
    
    @Autowired IClientesRepository clienteRepo;
    @Autowired IPersonasRepository personaRepo;

    @Override
    public Collection<ClienteDTO> getClientes(int pageSize, int pageIndex, Predicate condicion) {
        List<ClienteDTO> pagina = new ArrayList<>();
        Iterable<Cliente> clientes;
        long clienteCount;
        
        LOG.info("getClientes - Procesando solicitud...");
        Sort orden = Sort.by(Ferme.CLIENTE_DEFAULT_SORT_COLUMN).ascending();
        Pageable pgbl = PageRequest.of(pageIndex, pageSize, orden);
        
        LOG.info("getClientes - Llamando queries...");
        if (condicion == null) {
            clientes = clienteRepo.findAll(pgbl);
            clienteCount = clienteRepo.count();
        } else {
            clientes = clienteRepo.findAll(condicion, pgbl);
            clienteCount = clienteRepo.count(condicion);
        }
        LOG.info("getClientes - Se han encontrado "+clienteCount+" clientes con los filtros ingresados.");
        
        LOG.info("getClientes - Procesando resultados...");
        clientes.forEach((entity) -> {
            ClienteDTO dto = entity.toDTO();
            pagina.add(dto);
        });
        LOG.info("getClientes - Resultados procesados con éxito.");
        
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
                        bb.and(qCliente._id.eq(parsedValueI));
                        return bb; //match por id es único
                    case "personaId":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qCliente._persona._id.eq(parsedValueI));
                        return bb; //match por id de persona es único
                    case "nombre":
                        paramValue = "%" + paramValue + "%";
                        bb.and(qCliente._persona._nombreCompleto.likeIgnoreCase(paramValue));
                        break;
                    case "rut":
                        paramValue = "%" + paramValue.toUpperCase() + "%";
                        bb.and(qCliente._persona._rut.likeIgnoreCase(paramValue));
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
        try {
            entity = clienteRepo.saveAndFlush(entity);
        } catch (Exception exc) {
            LOG.error("No se pudo guardar el cliente", exc);
            return 0;
        }
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
