package cl.duoc.alumnos.ferme.services.imp;

import cl.duoc.alumnos.ferme.domain.entities.QRubro;
import cl.duoc.alumnos.ferme.domain.entities.Rubro;
import cl.duoc.alumnos.ferme.dto.RubroDTO;
import cl.duoc.alumnos.ferme.services.IRubrosService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.duoc.alumnos.ferme.domain.repositories.IRubrosRepository;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author got12
 */
@Service
public class RubrosService implements IRubrosService {
    
    @Autowired IRubrosRepository rubroRepo;
    @Autowired EntityManager em;
    private final static Logger LOG = LoggerFactory.getLogger(ProductosService.class);

    @Override
    public Rubro rubroDTOToEntity(RubroDTO dto) {
        
        Rubro entity = new Rubro();
        
        if (dto.getIdRubro() != null && dto.getIdRubro() != 0) {
            entity.setId(dto.getIdRubro());
        }
        entity.setDescripcion(dto.getDescripcionRubro());
        
        return entity;
    }

    @Override
    public RubroDTO rubroEntityToDTO(Rubro entity) {
        
        RubroDTO dto = new RubroDTO();
        
        dto.setIdRubro(entity.getId());
        dto.setDescripcionRubro(entity.getDescripcion());
        
        return dto;
    }

    @Override
    public Collection<RubroDTO> getRubros(Predicate condicion) {
        
        List<RubroDTO> pagina = new ArrayList<>();
        
        Iterable<Rubro> rubros;
        if (condicion == null) {
            rubros = rubroRepo.findAll();
        } else {
            rubros = rubroRepo.findAll(condicion);
        }
        
        rubros.forEach((entity) -> {
            RubroDTO dto = this.rubroEntityToDTO(entity);
            pagina.add(dto);
        });
        
        return pagina;
    }
    
    @Override
    public Predicate queryParamsMapToRubrosFilteringPredicate(Map<String,String> queryParamsMap){
        
        QRubro qRubro = QRubro.rubro;
        BooleanBuilder bb = new BooleanBuilder();
        for (String paramName : queryParamsMap.keySet()) {
            String paramValue = queryParamsMap.get(paramName);
            LOG.debug(paramName+"="+paramValue);
            try {
                Integer parsedValueI;
                switch (paramName) {
                    case "id":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qRubro.id.eq(parsedValueI));
                        return bb; //match por id es único
                    case "descripcion":
                        paramValue = "%" + paramValue.toUpperCase() + "%";
                        bb.and(qRubro.descripcion.upper().like(paramValue));
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
    public int saveRubro(RubroDTO dto) {
        
        Rubro entity = this.rubroDTOToEntity(dto);
        entity = rubroRepo.saveAndFlush(entity);
        return entity.getId();
    }

    @Override
    public boolean deleteRubro(Integer rubroId) {
        
        try {
            rubroRepo.deleteById(rubroId);
            return true;
        } catch (IllegalArgumentException exc) {
            LOG.error("Error al borrar Rubro con id " +rubroId, exc);
            return false;
        }
    }
    
}
