package cl.duoc.alumnos.ferme.services.imp;

import cl.duoc.alumnos.ferme.domain.entities.Rubro;
import cl.duoc.alumnos.ferme.domain.repositories.RubrosRepository;
import cl.duoc.alumnos.ferme.dto.RubroDTO;
import cl.duoc.alumnos.ferme.services.IRubrosService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author got12
 */
@Service
public class RubrosService implements IRubrosService {
    
    @Autowired RubrosRepository rubroRepo;

    @Override
    public Rubro rubroDTOToEntity(RubroDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RubroDTO rubroEntityToDTO(Rubro entity) {
        RubroDTO dto = new RubroDTO();
        
        dto.setIdRubro(entity.getIdRubro());
        dto.setDescripcionRubro(entity.getDescripcion());
        
        return dto;
    }

    @Override
    public Collection<RubroDTO> getRubros() {
        
        List<RubroDTO> pagina = new ArrayList<>();
        this.rubroRepo.findAll().forEach((entity) -> {
            RubroDTO dto = this.rubroEntityToDTO(entity);
            pagina.add(dto);
        });
        
        return pagina;
    }
    
}
