package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.domain.entities.FamiliaProducto;
import cl.duoc.alumnos.ferme.dto.FamiliaProductoDTO;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
public interface IFamiliasProductoService {
    
    public FamiliaProducto familiaProductoDTOToEntity(FamiliaProductoDTO dto);
    public FamiliaProductoDTO familiaProductoEntityToDTO(FamiliaProducto entity);
    
    public Collection<FamiliaProductoDTO> getFamiliasProductos();
    
    public Predicate queryParamsMapToFamiliasProductosFilteringPredicate(Map<String,String> queryParamsMap);
    
}
