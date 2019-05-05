package cl.duoc.alumnos.ferme.services.imp;

import cl.duoc.alumnos.ferme.domain.entities.Persona;
import cl.duoc.alumnos.ferme.dto.PersonaDTO;
import cl.duoc.alumnos.ferme.services.IPersonasService;
import org.springframework.stereotype.Service;

/**
 *
 * @author got12
 */
@Service
public class PersonasService implements IPersonasService {

    @Override
    public Persona personaDTOToEntity(PersonaDTO dto) {
        Persona entity = new Persona();
        
        if (dto.getIdPersona() != null && dto.getIdPersona() != 0) {
            entity.setId(dto.getIdPersona());
        }
        entity.setNombreCompleto(dto.getNombreCompletoPersona());
        entity.setRut(dto.getRutPersona());
        
        return entity;
    }

    @Override
    public PersonaDTO personaEntityToDTO(Persona entity) {
        PersonaDTO dto = new PersonaDTO();
        
        if (dto.getIdPersona() != null && dto.getIdPersona() != 0) {
            dto.setIdPersona(entity.getId());
        }
        dto.setNombreCompletoPersona(entity.getNombreCompleto());
        dto.setRutPersona(entity.getRut());
        
        return dto;
    }
    
}
