/*
 * Copyright (C) 2019 Benjamin Guillermo La Madrid <got12g at gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package cl.duoc.alumnos.ferme.services.imp;

import cl.duoc.alumnos.ferme.domain.entities.Producto;
import cl.duoc.alumnos.ferme.domain.repositories.ProductosRepository;
import cl.duoc.alumnos.ferme.dto.ProductoDTO;
import cl.duoc.alumnos.ferme.services.IProductosService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
public class ProductosService implements IProductosService {
    
    @Autowired private ProductosRepository productoRepo;

    @Override
    public Collection<ProductoDTO> getProductos(int pageSize, int pageIndex) {
        
        Pageable pgbl = PageRequest.of(pageIndex, pageSize);
        
        List<ProductoDTO> pagina = new ArrayList<>();
        this.productoRepo.findAll(pgbl).forEach((entity) -> {
            ProductoDTO dto = this.toDTO(entity);
            pagina.add(dto);
        });
        
        return pagina;
    }

    @Override
    public Producto toEntity(ProductoDTO dto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ProductoDTO toDTO(Producto entity) {
        ProductoDTO dto = new ProductoDTO();
        
        dto.setIdProducto(entity.getIdProducto());
        dto.setCodigo(entity.getCodigo());
        dto.setDescripcion(entity.getDescripcion());
        dto.setNombre(entity.getNombre());
        dto.setPrecio(entity.getPrecio());
        dto.setStockActual(entity.getStockActual());
        dto.setStockCritico(entity.getStockCritico());
        dto.setIdTipoProducto(entity.getTipoProducto().getIdTipoProducto());
        
        return dto;
    }
    
    
    
}
