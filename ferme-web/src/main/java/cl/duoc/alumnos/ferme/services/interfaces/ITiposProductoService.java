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
    
    /**
     * Obtiene todos los tipos de producto y los transforma a DTO.
     * @param condicion El objeto Predicate con los filtros a aplicar. Nulable.
     * @return Una colección de objetos DTO.
     */
    public Collection<TipoProductoDTO> getTiposProductos(Predicate condicion);
    
    /**
     * Genera una o varias condiciones como un objeto Predicate para filtrar 
     * tipos de producto, tomando un Map de parámetros como base.
     * @param queryParamsMap Un Map de parámetros (presumiblemente proveído por 
     * un query string).
     * @return Un objeto Predicate representando un conjunto de filtros.
     */
    public Predicate queryParamsMapToTiposProductosFilteringPredicate(Map<String,String> queryParamsMap);
    
    /**
     * 
     * Guarda (inserta o actualiza) el tipo de producto.
     * @param dto El objeto DTO de tipo de producto con la información respectiva a guardar.
     * @return El ID del registro guardado.
     */
    public int saveTipoProducto(TipoProductoDTO dto);
    
    /**
     * 
     * Elimina el registro (y la información) del tipo de producto especificada.
     * @param tipoProductoId El ID del tipo de producto a eliminar.
     * @return true si es exitoso, false si falla.
     */
    public boolean deleteTipoProducto(Integer tipoProductoId);
}
