package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.domain.entities.Producto;
import cl.duoc.alumnos.ferme.dto.ProductoDTO;
import java.util.Collection;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
public interface IProductosService {
    
    public Producto toEntity(ProductoDTO dto);
    public ProductoDTO toDTO(Producto entity);
    
    public Collection<ProductoDTO> getProductos(int pageSize, int pageIndex);
    
}
