package cl.duoc.alumnos.ferme.services.interfaces;

import cl.duoc.alumnos.ferme.dto.DetalleOrdenCompraDTO;
import cl.duoc.alumnos.ferme.dto.OrdenCompraDTO;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;
import javassist.NotFoundException;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
public interface IOrdenesCompraService {
    
    /**
     * Obtiene una página (colección) de orden de compras, con un tamaño determinado 
     * (pudiendo filtrarlos) y los transforma a objetos DTO.
     * @param pageSize El número de resultados que la página mostrará.
     * @param pageIndex El número de página (la primera es 0).
     * @param condicion El objeto Predicate con los filtros a aplicar. Nulable.
     * @return Una colección de objetos DTO.
     */
    public Collection<OrdenCompraDTO> getOrdenesCompra(int pageSize, int pageIndex, Predicate condicion);
    
    /**
     * Obtiene los detalles de la orden de compra ingresada.
     * @param ordenCompraId El ID de la orden de compra de la que se traerán sus detalles.
     * @return Una colección de objetos DTO.
     */
    public Collection<DetalleOrdenCompraDTO> getDetallesOrdenCompra(Integer ordenCompraId);
    
    /**
     * Genera una o varias condiciones como un objeto Predicate para filtrar 
     * ordenCompras, tomando un Map de parámetros como base.
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
    public Predicate queryParamsMapToOrdenesCompraFilteringPredicate(Map<String,String> queryParamsMap);
    
    /**
     * 
     * Guarda (inserta o actualiza) la orden de compra.
     * @param dto El objeto DTO de orden de compra con la información respectiva a guardar.
     * @return El ID del registro guardado.
     */
    public int saveOrdenCompra(OrdenCompraDTO dto) throws NotFoundException;
    
    /**
     * 
     * Elimina el registro (y la información) de la orden de compra especificada.
     * @param ordenCompraId El ID de la orden de compra a eliminar.
     * @return true si es exitoso, false si falla.
     */
    public boolean deleteOrdenCompra(Integer ordenCompraId);
    
}
