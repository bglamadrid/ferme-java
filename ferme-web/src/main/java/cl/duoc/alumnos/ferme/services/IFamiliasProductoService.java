package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.domain.entities.FamiliaProducto;
import cl.duoc.alumnos.ferme.dto.FamiliaProductoDTO;
import java.util.Collection;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
public interface IFamiliasProductoService {
    
    public FamiliaProducto familiaProductoDTOToEntity(FamiliaProductoDTO dto);
    public FamiliaProductoDTO familiaProductoEntityToDTO(FamiliaProducto entity);
    
    public Collection<FamiliaProductoDTO> getFamiliasProductos();
    
}
