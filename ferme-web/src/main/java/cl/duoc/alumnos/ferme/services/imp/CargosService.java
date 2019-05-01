package cl.duoc.alumnos.ferme.services.imp;

import cl.duoc.alumnos.ferme.domain.entities.Cargo;
import cl.duoc.alumnos.ferme.domain.entities.QCargo;
import cl.duoc.alumnos.ferme.domain.repositories.ICargosRepository;
import cl.duoc.alumnos.ferme.dto.CargoDTO;
import com.querydsl.core.types.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.duoc.alumnos.ferme.services.ICargosService;
import com.querydsl.core.BooleanBuilder;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public Cargo cargoDTOToEntity(CargoDTO dto) {
        Cargo entity = new Cargo();
        
        if (dto.getIdCargo()!= null && dto.getIdCargo() != 0) {
            entity.setId(dto.getIdCargo());
        }
        entity.setDescripcion(dto.getDescripcionCargo());
        
        return entity;
    }

    @Override
    public CargoDTO cargoEntityToDTO(Cargo entity) {
        CargoDTO dto = new CargoDTO();
        
        dto.setIdCargo(entity.getId());
        dto.setDescripcionCargo(entity.getDescripcion());
        
        return dto;
    }

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
            CargoDTO dto = this.cargoEntityToDTO(entity);
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

    
    
}
