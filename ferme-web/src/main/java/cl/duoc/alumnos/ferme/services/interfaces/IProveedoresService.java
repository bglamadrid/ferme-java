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
    
    public Collection<ProveedorDTO> getProveedores();
    
    public Predicate queryParamsMapToProveedoresFilteringPredicate(Map<String,String> queryParamsMap);
    
}
