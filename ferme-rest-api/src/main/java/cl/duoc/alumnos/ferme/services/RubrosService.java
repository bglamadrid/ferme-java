package cl.duoc.alumnos.ferme.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.domain.entities.QRubro;
import cl.duoc.alumnos.ferme.domain.entities.Rubro;
import cl.duoc.alumnos.ferme.domain.repositories.IRubrosRepository;
import cl.duoc.alumnos.ferme.dto.RubroDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IRubrosService;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Service
public class RubrosService implements IRubrosService {
    private final static Logger LOG = LoggerFactory.getLogger(RubrosService.class);
    
    @Autowired IRubrosRepository rubroRepo;
    @Autowired EntityManager em;

    @Override
    public Collection<RubroDTO> getRubros(Predicate condicion) {
        List<RubroDTO> pagina = new ArrayList<>();
        Iterable<Rubro> rubros;
        long rubroCount;
        
        LOG.info("getRubros - Procesando solicitud...");
        Sort orden = Sort.by(FermeConfig.COLUMNAS_ORDENAMIENTO_MAPA.get(Rubro.class)).ascending();
        
        LOG.info("getRubros - Llamando queries...");
        if (condicion == null) {
            rubros = rubroRepo.findAll(orden);
            rubroCount = rubroRepo.count();
        } else {
            rubros = rubroRepo.findAll(condicion, orden);
            rubroCount = rubroRepo.count(condicion);
        }
        LOG.info("getRubros - Se han encontrado "+rubroCount+" rubros con los filtros ingresados.");
        
        LOG.info("getRubros - Procesando resultados...");
        rubros.forEach((entity) -> {
            RubroDTO dto = entity.toDTO();
            pagina.add(dto);
        });
        LOG.info("getRubros - Resultados procesados con éxito.");
        
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
                        bb.and(qRubro._id.eq(parsedValueI));
                        return bb; //match por id es único
                    case "descripcion":
                        paramValue = "%" + paramValue + "%";
                        bb.and(qRubro._descripcion.likeIgnoreCase(paramValue));
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
        
        Rubro entity = dto.toEntity();
        entity = rubroRepo.saveAndFlush(entity);
        return entity.getId();
    }

    @Override
    public boolean deleteRubro(Integer rubroId) {
        
        try {
            rubroRepo.deleteById(rubroId);
            return true;
        } catch (EmptyResultDataAccessException exc) {
            LOG.error("Error al borrar Rubro con id " +rubroId, exc);
            return false;
        }
    }
    
}
