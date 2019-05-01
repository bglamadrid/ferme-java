package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.domain.entities.Usuario;
import cl.duoc.alumnos.ferme.dto.UsuarioDTO;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
public interface IUsuariosService {
    
    public Usuario usuarioDTOToEntity(UsuarioDTO dto);
    public UsuarioDTO usuarioEntityToDTO(Usuario entity);
    
    public Collection<UsuarioDTO> getUsuarios();
    
    public Predicate queryParamsMapToUsuariosFilteringPredicate(Map<String,String> queryParamsMap);
    
}
