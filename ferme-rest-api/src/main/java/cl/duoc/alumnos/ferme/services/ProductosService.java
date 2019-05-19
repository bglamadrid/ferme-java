package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.domain.entities.FamiliaProducto;
import cl.duoc.alumnos.ferme.domain.entities.Producto;
import cl.duoc.alumnos.ferme.domain.entities.QFamiliaProducto;
import cl.duoc.alumnos.ferme.domain.entities.QProducto;
import cl.duoc.alumnos.ferme.domain.entities.QTipoProducto;
import cl.duoc.alumnos.ferme.domain.entities.TipoProducto;
import cl.duoc.alumnos.ferme.domain.repositories.IFamiliasProductosRepository;
import cl.duoc.alumnos.ferme.domain.repositories.IFunctionsRepository;
import cl.duoc.alumnos.ferme.domain.repositories.IProductosRepository;
import cl.duoc.alumnos.ferme.domain.repositories.ITiposProductosRepository;
import cl.duoc.alumnos.ferme.dto.FamiliaProductoDTO;
import cl.duoc.alumnos.ferme.dto.ProductoDTO;
import cl.duoc.alumnos.ferme.dto.TipoProductoDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IFamiliasProductoService;
import cl.duoc.alumnos.ferme.services.interfaces.IProductosService;
import cl.duoc.alumnos.ferme.services.interfaces.ITiposProductoService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    private final static Logger LOG = LoggerFactory.getLogger(ProductosService.class);

    @Override
    public Collection<FamiliaProductoDTO> getFamiliasProductos(Predicate condicion) {
        List<FamiliaProductoDTO> lista = new ArrayList<>();
        Iterable<FamiliaProducto> productos;
        
        if (condicion == null) {
            productos = this.fmlProductoRepo.findAll();
        } else {
            productos = this.fmlProductoRepo.findAll(condicion);
        }
        
        productos.forEach((entity) -> {
            FamiliaProductoDTO dto = entity.toDTO();
            lista.add(dto);
        });
        
        return lista;
    }

    @Override
    public Collection<TipoProductoDTO> getTiposProductos(Predicate condicion) {
        List<TipoProductoDTO> lista = new ArrayList<>();
        Iterable<TipoProducto> productos;
        
        if (condicion == null) {
            productos = this.tpProductoRepo.findAll();
        } else {
            productos = this.tpProductoRepo.findAll(condicion);
        }
        
        productos.forEach((entity) -> {
            TipoProductoDTO dto = entity.toDTO();
            lista.add(dto);
        });
        
        return lista;
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
            ProductoDTO dto = entity.toDTO();
            dto.setCodigoProducto(funcRepo.getProductoCodigo(dto.getIdProducto()));
            pagina.add(dto);
        });
        
        return pagina;
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
    public int saveFamiliaProducto(FamiliaProductoDTO dto) {
        
        FamiliaProducto entity = dto.toEntity();
        entity = fmlProductoRepo.saveAndFlush(entity);
        return entity.getId();
    }

    @Override
    public int saveTipoProducto(TipoProductoDTO dto) {
        
        TipoProducto entity = dto.toEntity();
        entity = tpProductoRepo.saveAndFlush(entity);
        return entity.getId();
    }

    @Override
    public int saveProducto(ProductoDTO dto) {
        
        Producto entity = dto.toEntity();
        entity = productoRepo.saveAndFlush(entity);
        return entity.getId();
    }

    @Override
    public boolean deleteFamiliaProducto(Integer familiaId) {
        
        try {
            productoRepo.deleteById(familiaId);
            return true;
        } catch (EmptyResultDataAccessException exc) {
            LOG.error("Error al borrar FamiliaProducto con id " +familiaId, exc);
        }
        return false;
    }

    @Override
    public boolean deleteTipoProducto(Integer tipoProductoId) {
        
        try {
            tpProductoRepo.deleteById(tipoProductoId);
            return true;
        } catch (EmptyResultDataAccessException exc) {
            LOG.error("Error al borrar TipoProducto con id " +tipoProductoId, exc);
        }
        return false;
    }

    @Override
    public boolean deleteProducto(Integer productoId) {
        
        try {
            productoRepo.deleteById(productoId);
            return true;
        } catch (EmptyResultDataAccessException exc) {
            LOG.error("Error al borrar Producto con id " +productoId, exc);
        }
        return false;
    }
    
}
