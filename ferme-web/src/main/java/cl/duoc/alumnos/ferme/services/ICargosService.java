package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.domain.entities.Cargo;
import cl.duoc.alumnos.ferme.dto.CargoDTO;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
public interface ICargosService {
    
    public Cargo cargoDTOToEntity(CargoDTO dto);
    public CargoDTO cargoEntityToDTO(Cargo entity);
    
    public Collection<CargoDTO> getCargos(Predicate filtros);
    
    public Predicate queryParamsMapToCargosFilteringPredicate(Map<String,String> queryParamsMap);
    
    public int saveCargo(CargoDTO dto);
    
    public boolean deleteCargo(Integer cargoId);
    
}
