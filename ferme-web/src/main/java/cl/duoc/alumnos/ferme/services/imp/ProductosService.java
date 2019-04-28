package cl.duoc.alumnos.ferme.services.imp;

import cl.duoc.alumnos.ferme.domain.entities.FamiliaProducto;
import cl.duoc.alumnos.ferme.domain.entities.Producto;
import cl.duoc.alumnos.ferme.domain.entities.Rubro;
import cl.duoc.alumnos.ferme.domain.entities.TipoProducto;
import cl.duoc.alumnos.ferme.dto.FamiliaProductoDTO;
import cl.duoc.alumnos.ferme.dto.ProductoDTO;
import cl.duoc.alumnos.ferme.dto.TipoProductoDTO;
import cl.duoc.alumnos.ferme.services.IFamiliasProductoService;
import cl.duoc.alumnos.ferme.services.IProductosService;
import cl.duoc.alumnos.ferme.services.ITiposProductoService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import cl.duoc.alumnos.ferme.domain.repositories.IFamiliasProductosRepository;
import cl.duoc.alumnos.ferme.domain.repositories.IProductosRepository;
import cl.duoc.alumnos.ferme.domain.repositories.IRubrosRepository;
import cl.duoc.alumnos.ferme.domain.repositories.ITiposProductosRepository;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
@Service
public class ProductosService implements IProductosService, IFamiliasProductoService, ITiposProductoService {
    
    @Autowired private IProductosRepository productoRepo;
    @Autowired private IFamiliasProductosRepository fmlProductoRepo;
    @Autowired private ITiposProductosRepository tpProductoRepo;
    @Autowired private IRubrosRepository rubroRepo;

    @Override
    public Producto productoDTOToEntity(ProductoDTO dto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ProductoDTO productoEntityToDTO(Producto entity) {
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

    @Override
    public FamiliaProducto familiaProductoDTOToEntity(FamiliaProductoDTO dto) {
        FamiliaProducto entity = new FamiliaProducto();
        Rubro rubroEntity = this.rubroRepo.getOne(dto.getIdFamiliaProducto());
        
        entity.setIdFamiliaProducto(dto.getIdFamiliaProducto());
        entity.setRubro(rubroEntity);
        entity.setDescripcion(dto.getDescripcionFamiliaProducto());
        
        return entity;
    }

    @Override
    public FamiliaProductoDTO familiaProductoEntityToDTO(FamiliaProducto entity) {
        FamiliaProductoDTO dto = new FamiliaProductoDTO();
        
        dto.setIdFamiliaProducto(entity.getIdFamiliaProducto());
        dto.setIdRubro(entity.getRubro().getIdRubro());
        dto.setDescripcionFamiliaProducto(entity.getDescripcion());
        
        return dto;
    }

    @Override
    public TipoProducto tipoProductoDTOToEntity(TipoProductoDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoProductoDTO tipoProductoEntityToDTO(TipoProducto entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Collection<ProductoDTO> getProductos(int pageSize, int pageIndex) {
        
        Pageable pgbl = PageRequest.of(pageIndex, pageSize);
        
        List<ProductoDTO> pagina = new ArrayList<>();
        this.productoRepo.findAll(pgbl).forEach((entity) -> {
            ProductoDTO dto = this.productoEntityToDTO(entity);
            pagina.add(dto);
        });
        
        return pagina;
    }

    @Override
    public Collection<FamiliaProductoDTO> getFamiliasProductos() {
        List<FamiliaProductoDTO> lista = new ArrayList<>();
        this.fmlProductoRepo.findAll().forEach((entity) -> {
            FamiliaProductoDTO dto = this.familiaProductoEntityToDTO(entity);
            lista.add(dto);
        });
        
        return lista;
    }

    @Override
    public Collection<TipoProductoDTO> getTiposProductos() {
        List<TipoProductoDTO> lista = new ArrayList<>();
        this.tpProductoRepo.findAll().forEach((entity) -> {
            TipoProductoDTO dto = this.tipoProductoEntityToDTO(entity);
            lista.add(dto);
        });
        
        return lista;
    }
    
}
