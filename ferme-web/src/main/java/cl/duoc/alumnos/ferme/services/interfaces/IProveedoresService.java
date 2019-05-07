package cl.duoc.alumnos.ferme.services.interfaces;

import cl.duoc.alumnos.ferme.domain.entities.Proveedor;
import cl.duoc.alumnos.ferme.dto.ProveedorDTO;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
public interface IProveedoresService {
    
    public Proveedor proveedorDTOToEntity(ProveedorDTO dto);
    public ProveedorDTO proveedorEntityToDTO(Proveedor entity);
    
    /**
     * Obtiene una página (colección) de proveedores, con un tamaño determinado 
     * (pudiendo filtrarlos) y los transforma a objetos DTO.
     * @param pageSize El número de resultados que la página mostrará.
     * @param pageIndex El número de página (la primera es 0).
     * @param condicion El objeto Predicate con los filtros a aplicar. Nulable.
     * @return Una colección de objetos DTO.
     */
    public Collection<ProveedorDTO> getProveedores(int pageSize, int pageIndex, Predicate condicion);
    
    /**
     * Genera una o varias condiciones como un objeto Predicate para filtrar 
     * proveedores, tomando un Map de parámetros como base.
     * @param queryParamsMap Un Map de parámetros (presumiblemente proveído por 
     * un query string).
     * @return Un objeto Predicate representando un conjunto de filtros.
     */
    public Predicate queryParamsMapToProveedoresFilteringPredicate(Map<String,String> queryParamsMap);
    
    /**
     * 
     * Guarda (inserta o actualiza) el proveedor.
     * @param dto El objeto DTO de proveedor con la información respectiva a guardar.
     * @return El ID del registro guardado.
     */
    public int saveProveedor(ProveedorDTO dto);
    
    /**
     * 
     * Elimina el registro (y la información) del proveedor especificada.
     * @param proveedorId El ID del proveedor a eliminar.
     * @return true si es exitoso, false si falla.
     */
    public boolean deleteProveedor(Integer proveedorId);
}
