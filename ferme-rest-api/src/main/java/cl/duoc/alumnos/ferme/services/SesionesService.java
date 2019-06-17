package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.dto.SesionDTO;
import cl.duoc.alumnos.ferme.dto.UsuarioDTO;
import cl.duoc.alumnos.ferme.services.interfaces.ISesionesService;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author 12g
 */
@Service
public class SesionesService implements ISesionesService {

    @Override
    public Collection<SesionDTO> getSesiones(int pageSize, int pageIndex, Predicate condicion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Predicate queryParamsMapToSesionesFilteringPredicate(Map<String, String> queryParamsMap) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SesionDTO abrirSesion(UsuarioDTO usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean validarSesion(SesionDTO sesion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean cerrarSesion(SesionDTO sesion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
