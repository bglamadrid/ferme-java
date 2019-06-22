package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.domain.entities.Persona;
import cl.duoc.alumnos.ferme.domain.entities.QPersona;
import cl.duoc.alumnos.ferme.domain.repositories.IPersonasRepository;
import cl.duoc.alumnos.ferme.dto.PersonaDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IPersonasService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Service
public class PersonasService implements IPersonasService {
    private final static Logger LOG = LoggerFactory.getLogger(PersonasService.class);
    
    @Autowired IPersonasRepository personaRepo;

    @Override
    public Collection<PersonaDTO> getPersonas(int pageSize, int pageIndex, Predicate condicion) {
        List<PersonaDTO> pagina = new ArrayList<>();
        Iterable<Persona> pesonas;
        long pesonaCount;
        
        LOG.info("getPersonas - Procesando solicitud...");
        Sort orden = Sort.by(Ferme.PERSONA_DEFAULT_SORT_COLUMN).ascending();
        Pageable pgbl = PageRequest.of(pageIndex, pageSize, orden);
        
        LOG.info("getPersonas - Llamando queries...");
        if (condicion == null) {
            pesonas = personaRepo.findAll(pgbl);
            pesonaCount = personaRepo.count();
        } else {
            pesonas = personaRepo.findAll(condicion, pgbl);
            pesonaCount = personaRepo.count(condicion);
        }
        LOG.info("getPersonas - Se han encontrado "+pesonaCount+" personas con los filtros ingresados.");
        
        LOG.info("getPersonas - Procesando resultados...");
        pesonas.forEach((entity) -> {
            PersonaDTO dto = entity.toDTO();
            pagina.add(dto);
        });
        LOG.info("getPersonas - Resultados procesados con éxito.");
        
        return pagina;
    }

    @Override
    public Predicate queryParamsMapToPersonasFilteringPredicate(Map<String, String> queryParamsMap) {
        
        QPersona qPersona = QPersona.persona;
        BooleanBuilder bb = new BooleanBuilder();
        for (String paramName : queryParamsMap.keySet()) {
            String paramValue = queryParamsMap.get(paramName);
            LOG.debug(paramName+"="+paramValue);
            try {
                Integer parsedValueI;
                switch (paramName) {
                    case "id":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qPersona._id.eq(parsedValueI));
                        return bb; //match por id es único
                    case "nombre":
                        paramValue = "%" + paramValue + "%";
                        bb.and(qPersona._nombreCompleto.likeIgnoreCase(paramValue));
                        break;
                    case "rut":
                        paramValue = "%" + paramValue + "%";
                        bb.and(qPersona._rut.likeIgnoreCase(paramValue));
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
