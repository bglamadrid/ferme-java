package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.domain.entities.Proveedor;
import cl.duoc.alumnos.ferme.domain.entities.Persona;
import cl.duoc.alumnos.ferme.domain.entities.QProveedor;
import cl.duoc.alumnos.ferme.domain.repositories.IProveedoresRepository;
import cl.duoc.alumnos.ferme.dto.ProveedorDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IProveedoresService;
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
public class ProveedoresService implements IProveedoresService {
    
    @Autowired IProveedoresRepository proveedorRepo;
    @Autowired EntityManager em;
    private final static Logger LOG = LoggerFactory.getLogger(ProductosService.class);

    @Override
    public Proveedor proveedorDTOToEntity(ProveedorDTO dto) throws NullPointerException {
        Proveedor entity = new Proveedor();
        
            if (dto.getIdProveedor()!= null && dto.getIdProveedor() != 0) {
                entity.setId(dto.getIdProveedor());
            }
            
        // Persona es la representación de la información personal de un Proveedor
        Integer personaId = dto.getIdPersona(); 
        Persona personaEntity;
        if (personaId == null || personaId == 0) { // si la persona es 'nueva', sólo reamos manualmente el Entity
            personaEntity = new Persona();
        } else { // de lo contrario, buscamos al Proveedor en base a la Persona
            BooleanExpression wherePersonaIdIsThisPersonasId = QProveedor.proveedor.persona.id.eq(personaId);
            try {
                Optional<Proveedor> realProveedor = proveedorRepo.findOne(wherePersonaIdIsThisPersonasId);
                personaEntity = realProveedor.get().getPersona();
            } catch (NoSuchElementException exc) {
                personaEntity = new Persona();
                LOG.warn("No se encontró un Proveedor asociado a Persona[ idPersona="+personaId+"], se realizó una conversión manual.", exc);
            } catch (IncorrectResultSizeDataAccessException exc) {
                List<Proveedor> realProveedores = new ArrayList<>();
                proveedorRepo.findAll(wherePersonaIdIsThisPersonasId).forEach(realProveedores::add);
                entity = realProveedores.get(realProveedores.size()-1);
                personaEntity = entity.getPersona();
                LOG.warn("Muchos Proveedor asociados a Persona[ idPersona="+personaId+"], el Proveedor elegido es el último creado.", exc);
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
    public ProveedorDTO proveedorEntityToDTO(Proveedor entity) {
        ProveedorDTO dto = new ProveedorDTO();
        Persona personaEntity = entity.getPersona();
        
        dto.setIdProveedor(entity.getId());
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
    public Collection<ProveedorDTO> getProveedores(int pageSize, int pageIndex, Predicate condicion) {
        Pageable pgbl = PageRequest.of(pageIndex, pageSize);
        
        List<ProveedorDTO> pagina = new ArrayList<>();
        Iterable<Proveedor> proveedores;
        
        if (condicion == null) {
            proveedores = proveedorRepo.findAll(pgbl);
        } else {
            proveedores = proveedorRepo.findAll(condicion, pgbl);
        }
        
        proveedores.forEach((entity) -> {
            ProveedorDTO dto = this.proveedorEntityToDTO(entity);
            pagina.add(dto);
        });
        
        return pagina;
    }

    @Override
    public Predicate queryParamsMapToProveedoresFilteringPredicate(Map<String, String> queryParamsMap) {
        
        QProveedor qProveedor = QProveedor.proveedor;
        BooleanBuilder bb = new BooleanBuilder();
        for (String paramName : queryParamsMap.keySet()) {
            String paramValue = queryParamsMap.get(paramName);
            LOG.debug(paramName+"="+paramValue);
            try {
                Integer parsedValueI;
                switch (paramName) {
                    case "id":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qProveedor.id.eq(parsedValueI));
                        return bb; //match por id es único
                    case "nombre":
                        paramValue = "%" + paramValue.toUpperCase() + "%";
                        bb.and(qProveedor.persona.nombreCompleto.upper().like(paramValue));
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
    public int saveProveedor(ProveedorDTO dto) {
        
        Proveedor entity = this.proveedorDTOToEntity(dto);
        entity = proveedorRepo.saveAndFlush(entity);
        return entity.getId();
    }

    @Override
    public boolean deleteProveedor(Integer proveedorid) {
        
        try {
            proveedorRepo.deleteById(proveedorid);
            return true;
        } catch (EmptyResultDataAccessException exc) {
            LOG.error("Error al borrar Proveedor con id " +proveedorid, exc);
        }
        return false;
    }

    
    
}
