package cl.duoc.alumnos.ferme.services.interfaces;

import cl.duoc.alumnos.ferme.domain.entities.OrdenCompra;
import cl.duoc.alumnos.ferme.dto.OrdenCompraDTO;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
public interface IOrdenesCompraService {
    
    public OrdenCompra ordenCompraDTOToEntity(OrdenCompraDTO dto);
    public OrdenCompraDTO ordenCompraEntityToDTO(OrdenCompra entity);
    
    /**
     * Obtiene todas las órdenes de compra a partir de un filtro determinado y 
     * las transforma a DTO.
     * @param condicion El objeto Predicate con los filtros. Puede ser null.
     * @return Una colección de objetos DTO.
     */
    public Collection<OrdenCompraDTO> getOrdenCompras(Predicate condicion);
    
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
    public Predicate queryParamsMapToOrdenComprasFilteringPredicate(Map<String,String> queryParamsMap);
    
}