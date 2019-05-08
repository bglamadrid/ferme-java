package cl.duoc.alumnos.ferme.services.interfaces;

import cl.duoc.alumnos.ferme.dto.EmpleadoDTO;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
public interface IEmpleadosService {
    
    /**
     * Obtiene una página (colección) de empleados, con un tamaño determinado 
     * (pudiendo filtrarlos) y los transforma a objetos DTO.
     * @param pageSize El número de resultados que la página mostrará.
     * @param pageIndex El número de página (la primera es 0).
     * @param condicion El objeto Predicate con los filtros a aplicar. Nulable.
     * @return Una colección de objetos DTO.
     */
    public Collection<EmpleadoDTO> getEmpleados(int pageSize, int pageIndex, Predicate condicion);
    
    /**
     * Genera una o varias condiciones como un objeto Predicate para filtrar 
     * empleados, tomando un Map de parámetros como base.
     * @param queryParamsMap Un Map de parámetros (presumiblemente proveído por 
     * un query string).
     * @return Un objeto Predicate representando un conjunto de filtros.
     */
    public Predicate queryParamsMapToEmpleadosFilteringPredicate(Map<String,String> queryParamsMap);
    
    /**
     * 
     * Guarda (inserta o actualiza) el empleado.
     * @param dto El objeto DTO de empleado con la información respectiva a guardar.
     * @return El ID del registro guardado.
     */
    public int saveEmpleado(EmpleadoDTO dto);
    
    /**
     * 
     * Elimina el registro (y la información) del empleado especificado.
     * @param empleadoId El ID del empleado a eliminar.
     * @return true si es exitoso, false si falla.
     */
    
    public boolean deleteEmpleado(Integer empleadoId);
    
}
