package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.Ferme;
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
import java.util.Optional;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
@Service
@Transactional
public class ProductosService implements IProductosService, IFamiliasProductoService, ITiposProductoService {
    
    @Autowired private IProductosRepository productoRepo;
    @Autowired private IFamiliasProductosRepository fmlProductoRepo;
    @Autowired private ITiposProductosRepository tpProductoRepo;
    private final static Logger LOG = LoggerFactory.getLogger(ProductosService.class);

    @Override
    public Collection<FamiliaProductoDTO> getFamiliasProductos(Predicate condicion) {
        List<FamiliaProductoDTO> lista = new ArrayList<>();
        Iterable<FamiliaProducto> familias;
        long familiaCount;
        
        LOG.info("getFamiliasProductos - Procesando solicitud...");
        Sort orden = Sort.by(Ferme.FAMILIA_PRODUCTO_DEFAULT_SORT_COLUMN).ascending();
        
        LOG.info("getFamiliasProductos - Llamando queries...");
        if (condicion == null) {
            familias = this.fmlProductoRepo.findAll(orden);
            familiaCount = this.fmlProductoRepo.count();
        } else {
            familias = this.fmlProductoRepo.findAll(condicion, orden);
            familiaCount = this.fmlProductoRepo.count(condicion);
        }
        LOG.info("getFamiliasProductos - Se han encontrado "+familiaCount+" familias de productos con los filtros ingresados.");
        
        LOG.info("getFamiliasProductos - Procesando resultados...");
        familias.forEach((entity) -> {
            FamiliaProductoDTO dto = entity.toDTO();
            lista.add(dto);
        });
        LOG.info("getFamiliasProductos - Resultados procesados con éxito.");
        
        return lista;
    }

    @Override
    public Collection<TipoProductoDTO> getTiposProductos(Predicate condicion) {
        List<TipoProductoDTO> lista = new ArrayList<>();
        Iterable<TipoProducto> tipos;
        Long tipoCount;
        
        LOG.info("getTiposProductos - Procesando solicitud...");
        Sort orden = Sort.by(Ferme.TIPO_PRODUCTO_DEFAULT_SORT_COLUMN).descending();
        
        LOG.info("getTiposProductos - Llamando queries...");
        if (condicion == null) {
            tipos = this.tpProductoRepo.findAll(orden);
            tipoCount = this.tpProductoRepo.count();
        } else {
            tipos = this.tpProductoRepo.findAll(condicion, orden);
            tipoCount = this.tpProductoRepo.count(condicion);
        }
        LOG.info("getTiposProductos - Se han encontrado "+tipoCount+" tipos de productos con los filtros ingresados.");
        
        LOG.info("getTiposProductos - Procesando resultados...");
        tipos.forEach((entity) -> {
            TipoProductoDTO dto = entity.toDTO();
            lista.add(dto);
        });
        LOG.info("getTiposProductos - Resultados procesados con éxito.");
        
        return lista;
    }
    
    @Override
    public Collection<ProductoDTO> getProductos(int pageSize, int pageIndex, Predicate condicion) {
        List<ProductoDTO> pagina = new ArrayList<>();
        Iterable<Producto> productos;
        Long productoCount;
        
        LOG.info("getProductos - Procesando solicitud...");
        Sort orden = Sort.by(Ferme.TIPO_PRODUCTO_DEFAULT_SORT_COLUMN).descending();
        Pageable pgbl = PageRequest.of(pageIndex, pageSize, orden);
        
        LOG.info("getProductos - Llamando queries...");
        if (condicion == null) {
            productos = this.productoRepo.findAll(pgbl);
            productoCount = this.productoRepo.count();
        } else {
            productos = this.productoRepo.findAll(condicion, pgbl);
            productoCount = this.productoRepo.count(condicion);
        }
        LOG.info("getProductos - Se han encontrado "+productoCount+" productos con los filtros ingresados.");
        
        LOG.info("getProductos - Procesando resultados...");
        productos.forEach((entity) -> {
            ProductoDTO dto = entity.toDTO();
            pagina.add(dto);
        });
        LOG.info("getProductos - Resultados procesados con éxito.");
        
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
                        paramValue = "%" + paramValue + "%";
                        bb.and(qFamilia.descripcion.likeIgnoreCase(paramValue));
                        break;
                    case "proveedor":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qFamilia.proveedor.id.eq(parsedValueI));
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
                        paramValue = "%" + paramValue + "%";
                        bb.and(qTipo.nombre.likeIgnoreCase(paramValue));
                        break;
                    case "familia":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qTipo.familia.id.eq(parsedValueI));
                        break;
                    case "proveedor":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qTipo.familia.proveedor.id.eq(parsedValueI));
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
                    case "codigo":
                        paramValue = "%" + paramValue + "%";
                        bb.and(qProducto.codigo.likeIgnoreCase(paramValue));
                        break;
                    case "nombre":
                        paramValue = "%" + paramValue + "%";
                        bb.and(qProducto.nombre.likeIgnoreCase(paramValue));
                        break;
                    case "descripcion":
                        paramValue = "%" + paramValue + "%";
                        bb.and(qProducto.descripcion.likeIgnoreCase(paramValue));
                        break;
                    case "tipo":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qProducto.tipo.id.eq(parsedValueI));
                        break;
                    case "familia":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qProducto.tipo.familia.id.eq(parsedValueI));
                        break;
                    case "proveedor":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qProducto.tipo.familia.proveedor.id.eq(parsedValueI));
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
    public int saveTipoProducto(TipoProductoDTO dto) throws NotFoundException {
        
        TipoProducto entity = dto.toEntity();
        Optional<FamiliaProducto> familiaEntity = fmlProductoRepo.findById(dto.getIdFamiliaProducto());
        if (familiaEntity.isPresent()) {
            entity.setFamilia(familiaEntity.get());
        } else {
            throw new NotFoundException("La familia asignada a este tipo no existe.");
        }
        entity = tpProductoRepo.saveAndFlush(entity);
        return entity.getId();
    }

    @Override
    public int saveProducto(ProductoDTO dto) throws NotFoundException {
        
        Producto entity = dto.toEntity();
        Optional<TipoProducto> tipoEntity = tpProductoRepo.findById(dto.getIdTipoProducto());
        if (tipoEntity.isPresent()) {
            entity.setTipo(tipoEntity.get());
        } else {
            throw new NotFoundException("El tipo asignado a este producto no existe.");
        }
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
