package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.domain.entities.FamiliaProducto;
import cl.duoc.alumnos.ferme.domain.entities.Producto;
import cl.duoc.alumnos.ferme.domain.entities.QFamiliaProducto;
import cl.duoc.alumnos.ferme.domain.entities.QProducto;
import cl.duoc.alumnos.ferme.domain.entities.QTipoProducto;
import cl.duoc.alumnos.ferme.domain.entities.Rubro;
import cl.duoc.alumnos.ferme.domain.entities.TipoProducto;
import cl.duoc.alumnos.ferme.dto.FamiliaProductoDTO;
import cl.duoc.alumnos.ferme.dto.ProductoDTO;
import cl.duoc.alumnos.ferme.dto.TipoProductoDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IFamiliasProductoService;
import cl.duoc.alumnos.ferme.services.interfaces.IProductosService;
import cl.duoc.alumnos.ferme.services.interfaces.ITiposProductoService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import cl.duoc.alumnos.ferme.domain.repositories.IFamiliasProductosRepository;
import cl.duoc.alumnos.ferme.domain.repositories.IFunctionsRepository;
import cl.duoc.alumnos.ferme.domain.repositories.IProductosRepository;
import cl.duoc.alumnos.ferme.domain.repositories.IRubrosRepository;
import cl.duoc.alumnos.ferme.domain.repositories.ITiposProductosRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
@Service
public class ProductosService implements IProductosService, IFamiliasProductoService, ITiposProductoService {
    
    @Autowired private IFunctionsRepository funcRepo;
    @Autowired private IProductosRepository productoRepo;
    @Autowired private IFamiliasProductosRepository fmlProductoRepo;
    @Autowired private ITiposProductosRepository tpProductoRepo;
    @Autowired private IRubrosRepository rubroRepo;
    private final static Logger LOG = LoggerFactory.getLogger(ProductosService.class);

    @Override
    public Producto productoDTOToEntity(ProductoDTO dto) {
        Producto entity = new Producto();
        Integer productoId = dto.getIdProducto();
        
        if (productoId != null && productoId != 0) {
            entity.setId(dto.getIdProducto());
        }
        entity.setNombre(dto.getNombreProducto());
        entity.setDescripcion(dto.getDescripcionProducto());
        
        return entity;
    }

    @Override
    public ProductoDTO productoEntityToDTO(Producto entity) {
        ProductoDTO dto = new ProductoDTO();
        Integer productoId = entity.getId();
        String codigo = funcRepo.getProductoCodigo(productoId);
        
        dto.setIdProducto(productoId);
        dto.setCodigoProducto(codigo);
        dto.setDescripcionProducto(entity.getDescripcion());
        dto.setNombreProducto(entity.getNombre());
        dto.setPrecioProducto(entity.getPrecio());
        dto.setStockActualProducto(entity.getStockActual());
        dto.setStockCriticoProducto(entity.getStockCritico());
        dto.setIdTipoProducto(entity.getTipo().getId());
        dto.setNombreTipoProducto(entity.getTipo().getNombre());
        
        return dto;
    }

    @Override
    public FamiliaProducto familiaProductoDTOToEntity(FamiliaProductoDTO dto) throws EntityNotFoundException {
        FamiliaProducto entity = new FamiliaProducto();
        Integer familiaProductoId = dto.getIdFamiliaProducto();
        Rubro rubroEntity = this.rubroRepo.getOne(dto.getIdFamiliaProducto());
        
        if (familiaProductoId != null && familiaProductoId != 0) {
            entity.setId(dto.getIdFamiliaProducto());
        }
        entity.setRubro(rubroEntity);
        entity.setDescripcion(dto.getDescripcionFamiliaProducto());
        
        return entity;
    }

    @Override
    public FamiliaProductoDTO familiaProductoEntityToDTO(FamiliaProducto entity) {
        Rubro rubroEntity = entity.getRubro();
        FamiliaProductoDTO dto = new FamiliaProductoDTO();
        
        dto.setIdFamiliaProducto(entity.getId());
        dto.setDescripcionFamiliaProducto(entity.getDescripcion());
        dto.setIdRubro(rubroEntity.getId());
        dto.setDescripcionRubro(rubroEntity.getDescripcion());
        
        return dto;
    }

    @Override
    public TipoProducto tipoProductoDTOToEntity(TipoProductoDTO dto) throws EntityNotFoundException {
        TipoProducto entity = new TipoProducto();
        Integer tipoProductoId = dto.getIdFamiliaProducto();
        FamiliaProducto familiaEntity = this.fmlProductoRepo.getOne(dto.getIdFamiliaProducto());
        
        if (tipoProductoId != null && tipoProductoId != 0) {
            entity.setId(tipoProductoId);
        }
        entity.setFamilia(familiaEntity);
        entity.setNombre(dto.getNombreFamiliaProducto());
        
        return entity;
    }

    @Override
    public TipoProductoDTO tipoProductoEntityToDTO(TipoProducto entity) {
        FamiliaProducto familiaProductoEntity = entity.getFamilia();
        TipoProductoDTO dto = new TipoProductoDTO();
        
        dto.setIdTipoProducto(entity.getId());
        dto.setNombreTipoProducto(entity.getNombre());
        dto.setIdFamiliaProducto(familiaProductoEntity.getId());
        dto.setNombreFamiliaProducto(familiaProductoEntity.getDescripcion());
        
        return dto;
    }
    
    @Override
    public Collection<ProductoDTO> getProductos(int pageSize, int pageIndex, Predicate condicion) {
        Pageable pgbl = PageRequest.of(pageIndex, pageSize);
        
        List<ProductoDTO> pagina = new ArrayList<>();
        Iterable<Producto> productos;
        
        if (condicion == null) {
            productos = this.productoRepo.findAll(pgbl);
        } else {
            productos = this.productoRepo.findAll(condicion, pgbl);
        }
        
        productos.forEach((entity) -> {
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

    @Override
    public Predicate queryParamsMapToProductosFilteringPredicate(Map<String, String> queryParamsMap) {
        QProducto qProducto = QProducto.producto;
        BooleanBuilder bb = new BooleanBuilder();
        for (String paramName : queryParamsMap.keySet()) {
            String paramValue = queryParamsMap.get(paramName);
            LOG.debug(paramName+"="+paramValue);
            try {
                Integer parsedValueI;
                Long parsedValueL;
                switch (paramName) {
                    case "id":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qProducto.id.eq(parsedValueI));
                        return bb; //match por id es único
                    case "nombre":
                        paramValue = "%" + paramValue.toUpperCase() + "%";
                        bb.and(qProducto.nombre.upper().like(paramValue.toUpperCase()));
                        break;
                    case "descripcion":
                        paramValue = "%" + paramValue.toUpperCase() + "%";
                        bb.and(qProducto.descripcion.upper().like(paramValue.toUpperCase()));
                        break;
                    case "precio":
                        parsedValueL = Long.valueOf(paramValue);
                        bb.and(qProducto.precio.eq(parsedValueL));
                        break;
                    case "stock":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qProducto.stockActual.eq(parsedValueI));
                        break;
                    default: break;
                }
            } catch (NumberFormatException exc) {
                LOG.error("No se pudo traducir el parámetro '" + paramName + "' a un número (su valor era '" + paramValue + "').", exc);
            }
        }
        
        return bb;
    }

    @Override
    public Predicate queryParamsMapToFamiliasProductosFilteringPredicate(Map<String, String> queryParamsMap) {
        QFamiliaProducto qFamilia = QFamiliaProducto.familiaProducto;
        BooleanBuilder bb = new BooleanBuilder();
        for (String paramName : queryParamsMap.keySet()) {
            String paramValue = queryParamsMap.get(paramName);
            LOG.debug(paramName+"="+paramValue);
            try {
                Integer parsedValueI;
                //Long parsedValueL;
                switch (paramName) {
                    case "id":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qFamilia.id.eq(parsedValueI));
                        return bb; //match por id es único
                    case "descripcion":
                        paramValue = "%" + paramValue.toUpperCase() + "%";
                        bb.and(qFamilia.descripcion.upper().like(paramValue.toUpperCase()));
                        break;
                    default: break;
                }
            } catch (NumberFormatException exc) {
                LOG.error("No se pudo traducir el parámetro '" + paramName + "' a un número (su valor era '" + paramValue + "').", exc);
            }
        }
        
        return bb;
    }

    @Override
    public Predicate queryParamsMapToTiposProductosFilteringPredicate(Map<String, String> queryParamsMap) {
        QTipoProducto qTipo = QTipoProducto.tipoProducto;
        BooleanBuilder bb = new BooleanBuilder();
        for (String paramName : queryParamsMap.keySet()) {
            String paramValue = queryParamsMap.get(paramName);
            LOG.debug(paramName+"="+paramValue);
            try {
                Integer parsedValueI;
                //Long parsedValueL;
                switch (paramName) {
                    case "id":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qTipo.id.eq(parsedValueI));
                        return bb; //match por id es único
                    case "nombre":
                        paramValue = "%" + paramValue.toUpperCase() + "%";
                        bb.and(qTipo.nombre.upper().like(paramValue.toUpperCase()));
                        break;
                    default: break;
                }
            } catch (NumberFormatException exc) {
                LOG.error("No se pudo traducir el parámetro '" + paramName + "' a un número (su valor era '" + paramValue + "').", exc);
            }
        }
        
        return bb;
    }
    
}
