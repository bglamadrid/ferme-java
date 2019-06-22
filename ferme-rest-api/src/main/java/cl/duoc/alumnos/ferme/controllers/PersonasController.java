package cl.duoc.alumnos.ferme.controllers;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.PersonaDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IPersonasService;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Benjamin Guillermo
 */
@RestController
@RequestMapping("/api/gestion")
public class PersonasController {
    private final static Logger LOG = LoggerFactory.getLogger(PersonasController.class);
    
    @Autowired private IPersonasService personaSvc;    
    
    @GetMapping("/personas")
    public Collection<PersonaDTO> obtener(@RequestParam Map<String,String> allRequestParams) {
        
        return this.obtener(null, null, allRequestParams);
    }
    
    @GetMapping("/personas/{pageSize}")
    public Collection<PersonaDTO> obtener(
            @PathVariable Integer pageSize,
            @RequestParam Map<String,String> allRequestParams
    ) {
        
        return this.obtener(pageSize, null, allRequestParams);
    }
    
    /**
     * Busca todos los personas. 
     * Si el URL tenía un query string (RequestParam, lo transforma a un Map, genera un Predicate a partir
     * de él y filtra la búsqueda con este Predicate.
     * @param pageSize
     * @param pageIndex
     * @param allRequestParams Un Map conteniendo una colección pares nombre/valor.
     * @see RequestParam
     * @see Predicate
     * @return Una colección de objetos PersonaDTO
     */
    @GetMapping("/personas/{pageSize}/{pageIndex}")
    public Collection<PersonaDTO> obtener(
        @PathVariable Integer pageSize,
        @PathVariable Integer pageIndex,
        @RequestParam Map<String,String> allRequestParams) {
        
        Integer finalPageSize = FermeConfig.DEFAULT_PAGE_SIZE;
        Integer finalPageIndex = FermeConfig.DEFAULT_PAGE_INDEX;
        Predicate filtros = null;
        
        if (pageSize != null && pageSize > 0) {
            finalPageSize = pageSize;
        }
        if (pageIndex != null && pageIndex > 0) {
            finalPageIndex = pageIndex-1;
        }
        if (allRequestParams != null && !allRequestParams.isEmpty()) {
            filtros = this.personaSvc.queryParamsMapToPersonasFilteringPredicate(allRequestParams);
        }        
        
        LOG.info("getPersonas - "+finalPageSize+" registros; página "+finalPageIndex);
        LOG.debug("getPersonas - Filtros solicitados: "+filtros);
        Collection<PersonaDTO> personas = personaSvc.getPersonas(finalPageSize, finalPageIndex, filtros);
        LOG.debug("getPersonas - personas.size()="+personas.size());
        LOG.info("getPersonas - Solicitud completa. Enviando respuesta al cliente.");
        return personas;
    }
}
