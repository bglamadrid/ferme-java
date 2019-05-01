package cl.duoc.alumnos.ferme.services.exceptions;

import javax.persistence.EntityNotFoundException;

/**
 *
 * @author got12
 */
public class FamiliaProductoNotFoundException extends EntityNotFoundException {
    
    public FamiliaProductoNotFoundException() {
        super();
    }
}
