package cl.duoc.alumnos.ferme.services.interfaces;

import cl.duoc.alumnos.ferme.domain.entities.TipoProducto;
import cl.duoc.alumnos.ferme.dto.TipoProductoDTO;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
public interface ITiposProductoService {
    
    public TipoProducto tipoProductoDTOToEntity(TipoProductoDTO dto);
    public TipoProductoDTO tipoProductoEntityToDTO(TipoProducto entity);
    
    public Collection<TipoProductoDTO> getTiposProductos();
    
    public Predicate queryParamsMapToTiposProductosFilteringPredicate(Map<String,String> queryParamsMap);
    
}
