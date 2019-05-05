package cl.duoc.alumnos.ferme.services.interfaces;

import cl.duoc.alumnos.ferme.domain.entities.Venta;
import cl.duoc.alumnos.ferme.dto.VentaDTO;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
public interface IVentasService {
    
    public Venta ventaDTOToEntity(VentaDTO dto);
    public VentaDTO ventaEntityToDTO(Venta entity);
    
    public Collection<VentaDTO> getVentas();
    
    public Predicate queryParamsMapToVentasFilteringPredicate(Map<String,String> queryParamsMap);
    
}
