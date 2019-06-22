package cl.duoc.alumnos.ferme.services.interfaces;

import cl.duoc.alumnos.ferme.dto.FamiliaProductoDTO;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;
import javassist.NotFoundException;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public interface IFamiliasProductoService {
    
    /**
     * Obtiene una página (colección) de familias, con filtro opcional, y 
     * los transforma a objetos DTO.
     * @param condicion El objeto Predicate con los filtros a aplicar. Nulable.
     * @return Una colección de objetos DTO.
     */
    public Collection<FamiliaProductoDTO> getFamiliasProductos(Predicate condicion);
    
    /**
     * Genera una o varias condiciones como un objeto Predicate para filtrar 
     * familias de productos, tomando un Map de parámetros como base.
     * @param queryParamsMap Un Map de parámetros (presumiblemente proveído por 
     * un query string).
     * @return Un objeto Predicate representando un conjunto de filtros.
     */
    public Predicate queryParamsMapToFamiliasProductosFilteringPredicate(Map<String,String> queryParamsMap);
    
    /**
     * 
     * Guarda (inserta o actualiza) la familia de productos.
     * @param dto El objeto DTO de familia de productos con la información 
     * respectiva a guardar.
     * @return El ID del registro guardado.
     */
    public int saveFamiliaProducto(FamiliaProductoDTO dto) throws NotFoundException;
    
    /**
     * 
     * Elimina el registro (y la información) de la familia de productos especificada.
     * @param familiaId El ID de la familia a eliminar.
     * @return true si es exitoso, false si falla.
     */
    public boolean deleteFamiliaProducto(Integer familiaId);
    
}
