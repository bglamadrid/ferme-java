package cl.duoc.alumnos.ferme.controllers;

import cl.duoc.alumnos.ferme.dto.CargoDTO;
import cl.duoc.alumnos.ferme.dto.RubroDTO;
import cl.duoc.alumnos.ferme.services.interfaces.ICargosService;
import cl.duoc.alumnos.ferme.services.interfaces.IRubrosService;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST que maneja todas las peticiones y obtención de entidades
 * independientes.
 * @author Benjamin Guillermo
 */
@RestController
@RequestMapping("/api/gestion")
public class ComunController {
    
    @Autowired private IRubrosService rubroSvc;
    @Autowired private ICargosService cargoSvc;
    private final static Logger LOG = LoggerFactory.getLogger(ComunController.class);
    
    
    /**
     * Busca todos los rubros. 
     * Si el URL tenía un query string (RequestParam, lo transforma a un Map, genera un Predicate a partir
     * de él y filtra la búsqueda con este Predicate.
     * @param allRequestParams Un Map conteniendo una colección pares nombre/valor.
     * @see RequestParam
     * @see Predicate
     * @return Una colección de objetos RubroDTO
     */
    @GetMapping({"/rubros", "/rubros/"})
    public Collection<RubroDTO> getRubros(@RequestParam Map<String,String> allRequestParams) {
        
        Predicate filtros = null;
        if (allRequestParams != null && !allRequestParams.isEmpty()) {
            filtros = this.rubroSvc.queryParamsMapToRubrosFilteringPredicate(allRequestParams);
        }
        return this.rubroSvc.getRubros(filtros);
    }
    
    /**
     * Almacena un Rubro nuevo o actualiza uno existente.
     * @param dto Un objeto DTO representando el Rubro a almacenar/actualizar.
     * @return El ID del rubro.
     */
    @PostMapping({"/rubros/guardar", "/rubros/guardar/"})
    public Integer saveRubro(@RequestBody RubroDTO dto) {
        
        if (dto != null) {
            return rubroSvc.saveRubro(dto);
        }
        return null;
    }
    
    /**
     * Elimina un Rubro de la base de datos.
     * @param rubroId El ID del Rubro a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue.
     */
    @PostMapping({"/rubros/borrar", "/rubros/borrar/"})
    public boolean deleteRubro(@RequestParam("id") Integer rubroId) {
        
        if (rubroId != null && rubroId != 0) {
            return rubroSvc.deleteRubro(rubroId);
        }
        return false;
    }
    
    
    /**
     * Busca todos los cargos. 
     * Si el URL tenía un query string (RequestParam, lo transforma a un Map, genera un Predicate a partir
     * de él y filtra la búsqueda con este Predicate.
     * @param allRequestParams Un Map conteniendo una colección pares nombre/valor.
     * @see RequestParam
     * @see Predicate
     * @return Una colección de objetos CargoDTO
     */
    @GetMapping({"/cargos", "/cargos/"})
    public Collection<CargoDTO> getCargos(@RequestParam Map<String,String> allRequestParams) {
        
        Predicate filtros = null;
        if (allRequestParams != null && !allRequestParams.isEmpty()) {
            filtros = this.cargoSvc.queryParamsMapToCargosFilteringPredicate(allRequestParams);
        }
        return this.cargoSvc.getCargos(filtros);
    }
    
    /**
     * Almacena un Cargo nuevo o actualiza uno existente.
     * @param dto Un objeto DTO representando el Rubro a almacenar/actualizar.
     * @return El ID del rubro.
     */
    @PostMapping({"/cargos/guardar", "/cargos/guardar/"})
    public Integer saveCargo(@RequestBody CargoDTO dto) {
        
        if (dto != null) {
            return cargoSvc.saveCargo(dto);
        }
        return null;
    }
    
    /**
     * Elimina un Rubro de la base de datos.
     * @param cargoId El ID del Rubro a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue.
     */
    @PostMapping({"/cargos/borrar", "/cargos/borrar/"})
    public boolean deleteCargo(@RequestParam("id") Integer cargoId) {
        
        if (cargoId != null && cargoId != 0) {
            return cargoSvc.deleteCargo(cargoId);
        }
        return false;
    }
}
