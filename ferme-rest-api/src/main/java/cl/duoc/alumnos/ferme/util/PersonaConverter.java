package cl.duoc.alumnos.ferme.util;

import cl.duoc.alumnos.ferme.domain.entities.Persona;
import cl.duoc.alumnos.ferme.dto.PersonaDTO;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public final class PersonaConverter {
    
    public final static <T extends PersonaDTO> T cargarDatosPersonaEnDTO(Persona personaEntity, T dto) {
        dto.setIdPersona(personaEntity.getId());
        dto.setNombreCompletoPersona(personaEntity.getNombreCompleto());
        dto.setRutPersona(personaEntity.getRut());
        
        String direccion = personaEntity.getDireccion();
        String email = personaEntity.getEmail();
        Long fono1 = personaEntity.getFono1();
        Long fono2 = personaEntity.getFono2();
        Long fono3 = personaEntity.getFono3();
        
        if (direccion != null) {
          dto.setDireccionPersona(direccion);
        }
        
        if (email != null) {
          dto.setEmailPersona(email);
        }
        
        if (fono1 != null) {
          dto.setFonoPersona1(fono1);
        }
        
        if (fono2 != null) {
          dto.setFonoPersona2(fono2);
        }
        
        if (fono3 != null) {
          dto.setFonoPersona3(fono3);
        }
        return dto;
    }
}
