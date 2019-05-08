package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.domain.entities.Empleado;
import cl.duoc.alumnos.ferme.domain.entities.QEmpleado;
import cl.duoc.alumnos.ferme.domain.repositories.IEmpleadosRepository;
import cl.duoc.alumnos.ferme.dto.EmpleadoDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IEmpleadosService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
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
public class EmpleadosService implements IEmpleadosService {
    
    @Autowired IEmpleadosRepository empleadoRepo;
    @Autowired EntityManager em;
    private final static Logger LOG = LoggerFactory.getLogger(ProductosService.class);

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
            EmpleadoDTO dto = entity.toDTO();
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
                    case "rut":
                        paramValue = "%" + paramValue.toUpperCase() + "%";
                        bb.and(qEmpleado.persona.rut.upper().like(paramValue));
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
        
        Empleado entity = dto.toEntity();
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
