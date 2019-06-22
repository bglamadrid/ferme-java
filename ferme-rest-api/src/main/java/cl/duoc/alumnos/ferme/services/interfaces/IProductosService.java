package cl.duoc.alumnos.ferme.services.interfaces;

import cl.duoc.alumnos.ferme.dto.ProductoDTO;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;
import javassist.NotFoundException;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public interface IProductosService {
    
    /**
     * Obtiene una página (colección) de productos, con un tamaño determinado 
     * (pudiendo filtrarlos) y los transforma a objetos DTO.
     * @param pageSize El número de resultados que la página mostrará.
     * @param pageIndex El número de página (la primera es 0).
     * @param condicion El objeto Predicate con los filtros a aplicar. Nulable.
     * @return Una colección de objetos DTO.
     */
    public Collection<ProductoDTO> getProductos(int pageSize, int pageIndex, Predicate condicion);
    
    /**
     * Genera una o varias condiciones como un objeto Predicate para filtrar 
     * productos, tomando un Map de parámetros como base.
     * @param queryParamsMap Un Map de parámetros (presumiblemente proveído por 
     * un query string).
     * @return Un objeto Predicate representando un conjunto de filtros.
     */
    public Predicate queryParamsMapToProductosFilteringPredicate(Map<String,String> queryParamsMap);
    
    /**
     * 
     * Guarda (inserta o actualiza) el producto.
     * @param dto El objeto DTO de producto con la información respectiva a guardar.
     * @return El ID del registro guardado.
     */
    public int saveProducto(ProductoDTO dto) throws NotFoundException;
    
    /**
     * 
     * Elimina el registro (y la información) del producto especificada.
     * @param productoId El ID del producto a eliminar.
     * @return true si es exitoso, false si falla.
     */
    public boolean deleteProducto(Integer productoId);
}
