package cl.duoc.alumnos.ferme.services;

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
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author got12
 */
@Service
public class CargosService implements ICargosService {
    
    @Autowired ICargosRepository cargoRepo;
    @Autowired EntityManager em;
    private final static Logger LOG = LoggerFactory.getLogger(ProductosService.class);

    @Override
    public Collection<CargoDTO> getCargos(Predicate condicion) {
        
        List<CargoDTO> pagina = new ArrayList<>();
        
        Iterable<Cargo> cargos;
        if (condicion == null) {
            cargos = cargoRepo.findAll();
        } else {
            cargos = cargoRepo.findAll(condicion);
        }
        
        cargos.forEach((entity) -> {
            CargoDTO dto = entity.toDTO();
            pagina.add(dto);
        });
        
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
                        bb.and(qCargo.id.eq(parsedValueI));
                        return bb; //match por id es único
                    case "descripcion":
                        paramValue = "%" + paramValue.toUpperCase() + "%";
                        bb.and(qCargo.descripcion.upper().like(paramValue));
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
