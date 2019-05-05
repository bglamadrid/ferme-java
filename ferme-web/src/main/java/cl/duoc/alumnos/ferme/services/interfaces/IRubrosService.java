package cl.duoc.alumnos.ferme.services.interfaces;

import cl.duoc.alumnos.ferme.domain.entities.Rubro;
import cl.duoc.alumnos.ferme.dto.RubroDTO;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
public interface IRubrosService {
    
    public Rubro rubroDTOToEntity(RubroDTO dto);
    public RubroDTO rubroEntityToDTO(Rubro entity);
    
    /**
     * Obtiene todos los rubros a partir de un filtro determinado y los 
     * transforma a DTO.
     * @param condicion El objeto Predicate con los filtros. Puede ser null.
     * @return Una colección de objetos DTO.
     */
    public Collection<RubroDTO> getRubros(Predicate condicion);
    
    /**
     * Genera una o varias condiciones como un objeto Predicate para filtrar 
     * rubros, tomando un Map de parámetros como base.
     * <br><br>
     * Los parámetros soportados son:
     * <ul>
     * <li><i>id</i></li>
     * <li><i>descripcion</i></li>
     * </ul>
     * @param queryParamsMap Un Map de parámetros (presumiblemente proveído por 
     * un query string).
     * @return Un objeto Predicate representando un conjunto de filtros.
     */
    public Predicate queryParamsMapToRubrosFilteringPredicate(Map<String,String> queryParamsMap);
    
    public int saveRubro(RubroDTO dto);
    
    public boolean deleteRubro(Integer rubroId);
    
}