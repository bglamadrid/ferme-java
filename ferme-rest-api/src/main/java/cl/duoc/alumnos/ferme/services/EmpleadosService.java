package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.domain.entities.Cargo;
import cl.duoc.alumnos.ferme.domain.entities.Empleado;
import cl.duoc.alumnos.ferme.domain.entities.QEmpleado;
import cl.duoc.alumnos.ferme.domain.repositories.ICargosRepository;
import cl.duoc.alumnos.ferme.domain.repositories.IEmpleadosRepository;
import cl.duoc.alumnos.ferme.domain.repositories.IPersonasRepository;
import cl.duoc.alumnos.ferme.dto.EmpleadoDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IEmpleadosService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javassist.NotFoundException;
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
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Service
@Transactional
public class EmpleadosService implements IEmpleadosService {
    private final static Logger LOG = LoggerFactory.getLogger(EmpleadosService.class);
    
    @Autowired IEmpleadosRepository empleadoRepo;
    @Autowired IPersonasRepository personaRepo;
    @Autowired ICargosRepository cargoRepo;

    @Override
    public Collection<EmpleadoDTO> getEmpleados(int pageSize, int pageIndex, Predicate condicion) {
        List<EmpleadoDTO> pagina = new ArrayList<>();
        Iterable<Empleado> empleados;
        long empleadoCount;
        
        LOG.info("getEmpleados - Procesando solicitud...");
        Sort orden = Sort.by(FermeConfig.COLUMNAS_ORDENAMIENTO_MAPA.get(Empleado.class)).ascending();
        Pageable pgbl = PageRequest.of(pageIndex, pageSize, orden);
        
        LOG.info("getEmpleados - Llamando queries...");
        if (condicion == null) {
            empleados = empleadoRepo.findAll(pgbl);
            empleadoCount = empleadoRepo.count();
        } else {
            empleados = empleadoRepo.findAll(condicion, pgbl);
            empleadoCount = empleadoRepo.count(condicion);
        }
        LOG.info("getEmpleados - Se han encontrado "+empleadoCount+" empleados con los filtros ingresados.");
        
        LOG.info("getEmpleados - Procesando resultados...");
        empleados.forEach((entity) -> {
            EmpleadoDTO dto = entity.toDTO();
            pagina.add(dto);
        });
        LOG.info("getEmpleados - Resultados procesados con éxito.");
        
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
                        bb.and(qEmpleado._id.eq(parsedValueI));
                        return bb; //match por id es único
                    case "personaId":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qEmpleado._persona._id.eq(parsedValueI));
                        return bb; //match por id de persona es único
                    case "nombre":
                        paramValue = "%" + paramValue + "%";
                        bb.and(qEmpleado._persona._nombreCompleto.likeIgnoreCase(paramValue));
                        break;
                    case "rut":
                        paramValue = "%" + paramValue + "%";
                        bb.and(qEmpleado._persona._rut.likeIgnoreCase(paramValue));
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
    public int saveEmpleado(EmpleadoDTO dto) throws NotFoundException {
        
        Empleado entity = dto.toEntity();
        
        Optional<Cargo> cargoEntity = cargoRepo.findById(dto.getIdCargo());
        if (cargoEntity.isPresent()) {
            entity.setCargo(cargoEntity.get());
        } else {
            throw new NotFoundException("El cargo del empleado no existe");
        }
        
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
