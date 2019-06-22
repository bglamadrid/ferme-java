package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.domain.entities.Cargo;
import cl.duoc.alumnos.ferme.domain.entities.QCargo;
import cl.duoc.alumnos.ferme.domain.repositories.ICargosRepository;
import cl.duoc.alumnos.ferme.dto.CargoDTO;
import cl.duoc.alumnos.ferme.services.interfaces.ICargosService;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Service
public class CargosService implements ICargosService {
    
    @Autowired ICargosRepository cargoRepo;
    private final static Logger LOG = LoggerFactory.getLogger(ProductosService.class);

    @Override
    public Collection<CargoDTO> getCargos(Predicate condicion) {
        List<CargoDTO> pagina = new ArrayList<>();
        Iterable<Cargo> cargos;
        long cargoCount;
        
        LOG.info("getCargos - Procesando solicitud...");
        Sort orden = Sort.by(FermeConfig.CARGO_DEFAULT_SORT_COLUMN).ascending();
        
        LOG.info("getCargos - Llamando queries...");
        if (condicion == null) {
            cargos = cargoRepo.findAll(orden);
            cargoCount = cargoRepo.count();
        } else {
            cargos = cargoRepo.findAll(condicion, orden);
            cargoCount = cargoRepo.count(condicion);
        }
        LOG.info("getCargos - Se han encontrado "+cargoCount+" cargos con los filtros ingresados.");
        
        LOG.info("getCargos - Procesando resultados...");
        cargos.forEach((entity) -> {
            CargoDTO dto = entity.toDTO();
            pagina.add(dto);
        });
        LOG.info("getCargos - Resultados procesados con éxito.");
        
        return pagina;
    }

    @Override
    public Predicate queryParamsMapToCargosFilteringPredicate(Map<String, String> queryParamsMap) {
        
        QCargo qCargo = QCargo.cargo;
        BooleanBuilder bb = new BooleanBuilder();
        for (String paramName : queryParamsMap.keySet()) {
            String paramValue = queryParamsMap.get(paramName);
            LOG.debug(paramName+"="+paramValue);
            try {
                Integer parsedValueI;
                switch (paramName) {
                    case "id":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qCargo._id.eq(parsedValueI));
                        return bb; //match por id es único
                    case "descripcion":
                        paramValue = "%" + paramValue+ "%";
                        bb.and(qCargo._descripcion.likeIgnoreCase(paramValue));
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
    public int saveCargo(CargoDTO dto) {
        
        Cargo entity = dto.toEntity();
        entity = cargoRepo.saveAndFlush(entity);
        return entity.getId();
    }

    @Override
    public boolean deleteCargo(Integer cargoId) {
        
        try {
            cargoRepo.deleteById(cargoId);
            return true;
        } catch (EmptyResultDataAccessException exc) {
            LOG.error("Error al borrar Cargo con id " +cargoId, exc);
        }
        return false;
    }

    
    
}
