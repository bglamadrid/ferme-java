package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.domain.entities.Empleado;
import cl.duoc.alumnos.ferme.domain.entities.Persona;
import cl.duoc.alumnos.ferme.domain.entities.QEmpleado;
import cl.duoc.alumnos.ferme.domain.repositories.IEmpleadosRepository;
import cl.duoc.alumnos.ferme.dto.EmpleadoDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IEmpleadosService;
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
public class EmpleadosService implements IEmpleadosService {
    
    @Autowired IEmpleadosRepository empleadoRepo;
    @Autowired EntityManager em;
    private final static Logger LOG = LoggerFactory.getLogger(ProductosService.class);

    @Override
    public Empleado empleadoDTOToEntity(EmpleadoDTO dto) throws NullPointerException {
        Empleado entity = new Empleado();
        
            if (dto.getIdEmpleado()!= null && dto.getIdEmpleado() != 0) {
                entity.setId(dto.getIdEmpleado());
            }
            
        // Persona es la representación de la información personal de un Empleado
        Integer personaId = dto.getIdPersona(); 
        Persona personaEntity;
        if (personaId == null || personaId == 0) { // si la persona es 'nueva', sólo reamos manualmente el Entity
            personaEntity = new Persona();
        } else { // de lo contrario, buscamos al Empleado en base a la Persona
            BooleanExpression wherePersonaIdIsThisPersonasId = QEmpleado.empleado.persona.id.eq(personaId);
            try {
                Optional<Empleado> realEmpleado = empleadoRepo.findOne(wherePersonaIdIsThisPersonasId);
                personaEntity = realEmpleado.get().getPersona();
            } catch (NoSuchElementException exc) {
                personaEntity = new Persona();
                LOG.warn("No se encontró un Empleado asociado a Persona[ idPersona="+personaId+"], se realizó una conversión manual.", exc);
            } catch (IncorrectResultSizeDataAccessException exc) {
                List<Empleado> realEmpleados = new ArrayList<>();
                empleadoRepo.findAll(wherePersonaIdIsThisPersonasId).forEach(realEmpleados::add);
                entity = realEmpleados.get(realEmpleados.size()-1);
                personaEntity = entity.getPersona();
                LOG.warn("Muchos Empleado asociados a Persona[ idPersona="+personaId+"], el Empleado elegido es el último creado.", exc);
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
    public EmpleadoDTO empleadoEntityToDTO(Empleado entity) {
        EmpleadoDTO dto = new EmpleadoDTO();
        Persona personaEntity = entity.getPersona();
        
        dto.setIdEmpleado(entity.getId());
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
    public Collection<EmpleadoDTO> getEmpleados(int pageSize, int pageIndex, Predicate condicion) {
        Pageable pgbl = PageRequest.of(pageIndex, pageSize);
        
        List<EmpleadoDTO> pagina = new ArrayList<>();
        Iterable<Empleado> empleados;
        
        if (condicion == null) {
            empleados = empleadoRepo.findAll(pgbl);
        } else {
            empleados = empleadoRepo.findAll(condicion, pgbl);
        }
        
        empleados.forEach((entity) -> {
            EmpleadoDTO dto = this.empleadoEntityToDTO(entity);
            pagina.add(dto);
        });
        
        return pagina;
    }

    @Override
    public Predicate queryParamsMapToEmpleadosFilteringPredicate(Map<String, String> queryParamsMap) {
        
        QEmpleado qEmpleado = QEmpleado.empleado;
        BooleanBuilder bb = new BooleanBuilder();
        for (String paramName : queryParamsMap.keySet()) {
            String paramValue = queryParamsMap.get(paramName);
            LOG.debug(paramName+"="+paramValue);
            try {
                Integer parsedValueI;
                switch (paramName) {
                    case "id":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qEmpleado.id.eq(parsedValueI));
                        return bb; //match por id es único
                    case "nombre":
                        paramValue = "%" + paramValue.toUpperCase() + "%";
                        bb.and(qEmpleado.persona.nombreCompleto.upper().like(paramValue));
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
    public int saveEmpleado(EmpleadoDTO dto) {
        
        Empleado entity = this.empleadoDTOToEntity(dto);
        entity = empleadoRepo.saveAndFlush(entity);
        return entity.getId();
    }

    @Override
    public boolean deleteEmpleado(Integer empleadoId) {
        
        try {
            empleadoRepo.deleteById(empleadoId);
            return true;
        } catch (EmptyResultDataAccessException exc) {
            LOG.error("Error al borrar Empleado con id " +empleadoId, exc);
        }
        return false;
    }

    
    
}
