/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.alumnos.ferme.controllers.gestion;

import cl.duoc.alumnos.ferme.dto.RubroDTO;
import cl.duoc.alumnos.ferme.services.IRubrosService;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Benjamin Guillermo
 */
@RestController
@RequestMapping("/api/gestion")
public class ComunController {
    
    @Autowired private IRubrosService rubroSvc;
    
    /**
     * Busca todos los rubros. 
     * Si el URL tenía un query string (RequestParam, lo transforma a un Map, genera un Predicate a partir
     * de él y filtra la búsqueda con este Predicate.
     * @see RequestParam
     * @see Predicate
     * @return Una colección de objetos RubroDTO
     */
    @GetMapping({"/rubros", "/rubros/"})
    public Collection<RubroDTO> getRubros(@RequestParam Map<String,String> allRequestParams) {
        
        Predicate filtros = null;
        if (allRequestParams != null && !allRequestParams.isEmpty()) {
            filtros = this.rubroSvc.queryParamsMapToFilteringPredicate(allRequestParams);
        }
        return this.rubroSvc.getRubros(filtros);
    }
}
