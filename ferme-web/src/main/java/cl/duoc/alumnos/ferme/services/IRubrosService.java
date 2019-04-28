package cl.duoc.alumnos.ferme.services;

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
    
    public Predicate queryParamsMapToFilteringPredicate(Map<String,String> nombre);
    
}