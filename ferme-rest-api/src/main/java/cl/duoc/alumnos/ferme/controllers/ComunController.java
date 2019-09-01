package cl.duoc.alumnos.ferme.controllers;

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

import com.querydsl.core.types.Predicate;

import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.CargoDTO;
import cl.duoc.alumnos.ferme.dto.RubroDTO;
import cl.duoc.alumnos.ferme.services.interfaces.ICargosService;
import cl.duoc.alumnos.ferme.services.interfaces.IRubrosService;

/**
 * Controlador REST que maneja todas las peticiones y obtención de entidades
 * independientes.
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@RestController
@RequestMapping(FermeConfig.URI_BASE_REST_API+"/gestion")
public class ComunController {
    private final static Logger LOG = LoggerFactory.getLogger(ComunController.class);
    
    @Autowired private IRubrosService rubroSvc;
    @Autowired private ICargosService cargoSvc;
    
    
    /**
     * Busca todos los rubros. 
     * Si el URL tenía un query string (RequestParam, lo transforma a un Map, genera un Predicate a partir
     * de él y filtra la búsqueda con este Predicate.
     * @param allRequestParams Un Map conteniendo una colección pares nombre/valor.
     * @see RequestParam
     * @see Predicate
     * @return Una colección de objetos RubroDTO
     */
    @GetMapping("/rubros")
    public Collection<RubroDTO> obtenerRubros(
        @RequestParam Map<String,String> allRequestParams
    ) {
        LOG.info("obtenerRubros");
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
    @PostMapping("/rubros/guardar")
    public Integer guardarRubro(
        @RequestBody RubroDTO dto
    ) {
        LOG.info("guardarRubro");
        if (dto != null) {
            LOG.debug("guardarRubro - dto="+dto);
            return rubroSvc.saveRubro(dto);
        }
        return null;
    }
    
    /**
     * Elimina un Rubro de la base de datos.
     * @param rubroId El ID del Rubro a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue.
     */
    @PostMapping("/rubros/borrar")
    public boolean borrarRubro(
        @RequestParam("id") Integer rubroId
    ) {
        LOG.info("borrarRubro");
        if (rubroId != null && rubroId != 0) {
            LOG.debug("borrarRubro - rubroId="+rubroId);
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
    @GetMapping("/cargos")
    public Collection<CargoDTO> obtenerCargos(
        @RequestParam Map<String,String> allRequestParams
    ) {
        LOG.info("obtenerCargos");
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
    @PostMapping("/cargos/guardar")
    public Integer guardarCargo(
        @RequestBody CargoDTO dto
    ) {
        LOG.info("guardarCargo");
        if (dto != null) {
            LOG.debug("guardarCargo - dto="+dto);
            return cargoSvc.saveCargo(dto);
        }
        return null;
    }
    
    /**
     * Elimina un Rubro de la base de datos.
     * @param cargoId El ID del Rubro a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue.
     */
    @PostMapping("/cargos/borrar")
    public boolean borrarCargo(
        @RequestParam("id") Integer cargoId
    ) {
        LOG.info("borrarCargo");
        if (cargoId != null && cargoId != 0) {
            LOG.debug("borrarCargo - cargoId="+cargoId);
            return cargoSvc.deleteCargo(cargoId);
        }
        return false;
    }
}
