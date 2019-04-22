package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.domain.entities.TipoProducto;
import cl.duoc.alumnos.ferme.dto.TipoProductoDTO;
import java.util.Collection;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
public interface ITiposProductoService {
    
    public TipoProducto tipoProductoDTOToEntity(TipoProductoDTO dto);
    public TipoProductoDTO tipoProductoEntityToDTO(TipoProducto entity);
    
    public Collection<TipoProductoDTO> getTiposProductos();
    
}
