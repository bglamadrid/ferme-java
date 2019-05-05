package cl.duoc.alumnos.ferme.services.interfaces;

import cl.duoc.alumnos.ferme.domain.entities.Empleado;
import cl.duoc.alumnos.ferme.dto.EmpleadoDTO;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
public interface IEmpleadosService {
    
    public Empleado proveedorDTOToEntity(EmpleadoDTO dto);
    public EmpleadoDTO proveedorEntityToDTO(Empleado entity);
    
    public Collection<EmpleadoDTO> getEmpleados();
    
    public Predicate queryParamsMapToEmpleadosFilteringPredicate(Map<String,String> queryParamsMap);
    
}
