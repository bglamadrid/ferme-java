package cl.duoc.alumnos.ferme.services.interfaces;

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
    
    /**
     * Obtiene una página (colección) de usuarios, con un tamaño determinado 
     * (pudiendo filtrarlos) y los transforma a objetos DTO.
     * @param pageSize El número de resultados que la página mostrará.
     * @param pageIndex El número de página (la primera es 0).
     * @param condicion El objeto Predicate con los filtros a aplicar. Nulable.
     * @return Una colección de objetos DTO.
     */
    public Collection<UsuarioDTO> getUsuarios(int pageSize, int pageIndex, Predicate condicion);
    
    /**
     * Genera una o varias condiciones como un objeto Predicate para filtrar 
     * usuarios, tomando un Map de parámetros como base.
     * @param queryParamsMap Un Map de parámetros (presumiblemente proveído por 
     * un query string).
     * @return Un objeto Predicate representando un conjunto de filtros.
     */
    public Predicate queryParamsMapToUsuariosFilteringPredicate(Map<String,String> queryParamsMap);
    
    /**
     * 
     * Guarda (inserta o actualiza) el usuario.
     * @param dto El objeto DTO de usuario con la información respectiva a guardar.
     * @return El ID del registro guardado.
     */
    public int saveUsuario(UsuarioDTO dto);
    
    /**
     * 
     * Elimina el registro (y la información) del usuario especificada.
     * @param usuarioId El ID del usuario a eliminar.
     * @return true si es exitoso, false si falla.
     */
    public boolean deleteUsuario(Integer usuarioId);
    
}
