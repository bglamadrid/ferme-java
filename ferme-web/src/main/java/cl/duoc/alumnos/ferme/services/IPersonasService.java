package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.domain.entities.Persona;
import cl.duoc.alumnos.ferme.dto.PersonaDTO;

/**
 *
 * @author got12
 */
public interface IPersonasService {
    
    public Persona personaDTOToEntity(PersonaDTO dto);
    public PersonaDTO personaEntityToDTO(Persona entity);
}
