package cl.duoc.alumnos.ferme.services.interfaces;

import cl.duoc.alumnos.ferme.dto.SesionDTO;
import cl.duoc.alumnos.ferme.dto.UsuarioDTO;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public interface ISesionesService {
    
    /**
     * Obtiene una página (colección) de sesiones, con un tamaño determinado 
     * (pudiendo filtrarlos) y los transforma a objetos DTO.
     * @param pageSize El número de resultados que la página mostrará.
     * @param pageIndex El número de página (la primera es 0).
     * @param condicion El objeto Predicate con los filtros a aplicar. Nulable.
     * @return Una colección de objetos DTO.
     */
    public Collection<SesionDTO> getSesiones(int pageSize, int pageIndex, Predicate condicion);
    
    /**
     * Genera una o varias condiciones como un objeto Predicate para filtrar 
     * sesiones, tomando un Map de parámetros como base.
     * @param queryParamsMap Un Map de parámetros (presumiblemente proveído por 
     * un query string).
     * @return Un objeto Predicate representando un conjunto de filtros.
     */
    public Predicate queryParamsMapToSesionesFilteringPredicate(Map<String,String> queryParamsMap);
    
    
    public SesionDTO abrirSesion(UsuarioDTO usuario);
    
    /**
     * Busca sesiones abiertas en base al hash del DTO ingresado, y les pasa 
     * un algoritmo de validación.
     * El algoritmo es el siguiente: si a la fecha de apertura de la sesión se 
     * le suma el tiempo de duración de la sesión, y el resultado excede la 
     * fecha actual, la sesión pasa esta validación.<br>
     * Las sesiones que no pasan esta validación son cerradas.
     * @param sesion Un objeto DTO representando la sesión.
     * @return Si encuentra al menos una sesión que pase la validación mencionada arriba.
     */
    public boolean validarSesion(SesionDTO sesion);
    
    /**
     * Busca las sesiones asociadas al hash del DTO ingresado, y las cierra.
     * @param sesion
     * @return 
     */
    public boolean cerrarSesiones(SesionDTO sesion);
    
}
