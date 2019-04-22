package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.domain.entities.Rubro;
import cl.duoc.alumnos.ferme.dto.RubroDTO;
import java.util.Collection;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
public interface IRubrosService {
    
    public Rubro rubroDTOToEntity(RubroDTO dto);
    public RubroDTO rubroEntityToDTO(Rubro entity);
    
    public Collection<RubroDTO> getRubros();
    
}