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
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author got12
 */
@Service
public class RubrosService implements IRubrosService {
    
    @Autowired IRubrosRepository rubroRepo;
    @Autowired EntityManager em;

    @Override
    public Rubro rubroDTOToEntity(RubroDTO dto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RubroDTO rubroEntityToDTO(Rubro entity) {
        RubroDTO dto = new RubroDTO();
        
        dto.setIdRubro(entity.getIdRubro());
        dto.setDescripcionRubro(entity.getDescripcion());
        
        return dto;
    }

    @Override
    public Collection<RubroDTO> getRubros(Predicate condicion) {
        
        List<RubroDTO> pagina = new ArrayList<>();
        
        Iterable<Rubro> rubroIterable;
        if (condicion == null) {
            rubroIterable = rubroRepo.findAll();
        } else {
            rubroIterable = rubroRepo.findAll(condicion);
        }
        
        rubroIterable.forEach((entity) -> {
            RubroDTO dto = this.rubroEntityToDTO(entity);
            pagina.add(dto);
        });
        
        return pagina;
    }
    
    @Override
    public Predicate queryParamsMapToFilteringPredicate(Map<String,String> filtros){
        
        QRubro qRubro = QRubro.rubro;
        BooleanBuilder bb = new BooleanBuilder();
        for (String key : filtros.keySet()) {
            String value = filtros.get(key);
            switch (key) {
                case "id":
                    bb = bb.and(qRubro.idRubro.eq(Integer.valueOf(value))); //TODO validar que el valor sea entero
                    return bb; //match por id es único
                case "descripcion":
                    bb = bb.and(qRubro.descripcion.like(value));
                    break;
                default: break;
            }
        }
        
        return bb;
    }
    
}
