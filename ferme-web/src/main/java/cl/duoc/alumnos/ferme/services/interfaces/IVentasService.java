package cl.duoc.alumnos.ferme.services.interfaces;

import cl.duoc.alumnos.ferme.dto.VentaDTO;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
public interface IVentasService {
    
    /**
     * Obtiene una página (colección) de ventas, con un tamaño determinado 
     * (pudiendo filtrarlos) y los transforma a objetos DTO.
     * @param pageSize El número de resultados que la página mostrará.
     * @param pageIndex El número de página (la primera es 0).
     * @param condicion El objeto Predicate con los filtros a aplicar. Nulable.
     * @return Una colección de objetos DTO.
     */
    public Collection<VentaDTO> getVentas(int pageSize, int pageIndex, Predicate condicion);
    
    /**
     * Genera una o varias condiciones como un objeto Predicate para filtrar 
     * ventas, tomando un Map de parámetros como base.
     * @param queryParamsMap Un Map de parámetros (presumiblemente proveído por 
     * un query string).
     * @return Un objeto Predicate representando un conjunto de filtros.
     */
    public Predicate queryParamsMapToVentasFilteringPredicate(Map<String,String> queryParamsMap);
    
    /**
     * 
     * Guarda (inserta o actualiza) la venta.
     * @param dto El objeto DTO de venta con la información respectiva a guardar.
     * @return El ID del registro guardado.
     */
    public int saveVenta(VentaDTO dto);
    
    /**
     * 
     * Elimina el registro (y la información) de la venta especificada.
     * @param ventaId El ID d la venta a eliminar.
     * @return true si es exitoso, false si falla.
     */
    public boolean deleteVenta(Integer ventaId);
    
}
