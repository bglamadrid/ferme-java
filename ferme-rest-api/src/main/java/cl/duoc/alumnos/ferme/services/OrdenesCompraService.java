package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.domain.entities.Empleado;
import cl.duoc.alumnos.ferme.domain.entities.DetalleOrdenCompra;
import cl.duoc.alumnos.ferme.domain.entities.OrdenCompra;
import cl.duoc.alumnos.ferme.domain.entities.Producto;
import cl.duoc.alumnos.ferme.domain.entities.QDetalleOrdenCompra;
import cl.duoc.alumnos.ferme.domain.entities.QOrdenCompra;
import cl.duoc.alumnos.ferme.domain.entities.QProducto;
import cl.duoc.alumnos.ferme.domain.repositories.IDetallesOrdenesCompraRepository;
import cl.duoc.alumnos.ferme.domain.repositories.IEmpleadosRepository;
import cl.duoc.alumnos.ferme.domain.repositories.IOrdenesCompraRepository;
import cl.duoc.alumnos.ferme.domain.repositories.IProductosRepository;
import cl.duoc.alumnos.ferme.dto.DetalleOrdenCompraDTO;
import cl.duoc.alumnos.ferme.dto.OrdenCompraDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IOrdenesCompraService;
import cl.duoc.alumnos.ferme.util.FormatoFechas;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
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
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Service
@Transactional
public class OrdenesCompraService implements IOrdenesCompraService {
    
    @Autowired private IOrdenesCompraRepository ordenCompraRepo;
    @Autowired private IDetallesOrdenesCompraRepository detalleOrdenCompraRepo;
    //@Autowired private IDetallesOrdenesCompraRepository productoRepo;
    @Autowired private IEmpleadosRepository empleadoRepo;
    @Autowired private IProductosRepository productoRepo;
    
    private static final Logger LOG = LoggerFactory.getLogger(OrdenesCompraService.class);

    @Override
    public Integer getNextId() {
        Sort idSort = Sort.by(Sort.Direction.DESC, "_id");
        Iterator<OrdenCompra> it = ordenCompraRepo.findAll(idSort).iterator();
        if (it.hasNext()){
            return it.next().getId() +1 ;
        } else {
            return 1;
        }
    }
    
    @Override
    public Collection<OrdenCompraDTO> getOrdenesCompra(int pageSize, int pageIndex, Predicate condicion) {
        List<OrdenCompraDTO> pagina = new ArrayList<>();
        Iterable<OrdenCompra> ordenesCompra;
        long ordenCompraCount;
        
        LOG.info("getOrdenesCompra - Procesando solicitud...");
        Sort orden = Sort.by(FermeConfig.COLUMNAS_ORDENAMIENTO_MAPA.get(OrdenCompra.class)).descending();
        Pageable pgbl = PageRequest.of(pageIndex, pageSize, orden);
        
        LOG.info("getOrdenesCompra - Llamando queries...");
        if (condicion == null) {
            ordenesCompra = ordenCompraRepo.findAll(pgbl);
            ordenCompraCount = ordenCompraRepo.count();
        } else {
            ordenesCompra = ordenCompraRepo.findAll(condicion, pgbl);
            ordenCompraCount = ordenCompraRepo.count(condicion);
        }
        LOG.info("getOrdenesCompra - Se han encontrado "+ordenCompraCount+" órdenes de compra con los filtros ingresados.");
        
        LOG.info("getOrdenesCompra - Procesando resultados...");
        boolean conversionCompleta = (condicion != null && ordenCompraCount == 1);
        ordenesCompra.forEach((entity) -> {
            OrdenCompraDTO dto = entity.toDTO(conversionCompleta);
            
            if (conversionCompleta) {
                for (DetalleOrdenCompraDTO detalle : dto.getDetallesOrdenCompra()) {
                    Integer idProducto = detalle.getIdProducto();
                    BooleanBuilder bb = new BooleanBuilder().and(QProducto.producto._id.eq(idProducto));
                    Producto productoEntity = productoRepo.findAll(bb).iterator().next();
                    detalle.setCodigoProducto(productoEntity.getCodigo());
                    detalle.setNombreProducto(productoEntity.getNombre());
                    detalle.setPrecioProducto(productoEntity.getPrecio());
                }
            }
            pagina.add(dto);
        });
        LOG.info("getOrdenesCompra - Resultados procesados con éxito.");
        
        return pagina;
    }

    @Override
    public Collection<DetalleOrdenCompraDTO> getDetallesOrdenCompra(Integer ordenCompraId) {
        
        OrdenCompra entity = null;
        try {
            entity = ordenCompraRepo.getOne(ordenCompraId);
        } catch (Exception e) {
            LOG.error("No se pudo obtener la orden de compra con el ID " + ordenCompraId, e);
        }
        
        if (entity == null) {
            return null;
        } else {
            OrdenCompraDTO dtoReal = entity.toDTO(false);
            return dtoReal.getDetallesOrdenCompra();
        }
    }

    @Override
    public Predicate queryParamsMapToOrdenesCompraFilteringPredicate(Map<String, String> queryParamsMap) {
        
        QOrdenCompra qOrdenCompra = QOrdenCompra.ordenCompra;
        BooleanBuilder bb = new BooleanBuilder();
        for (String paramName : queryParamsMap.keySet()) {
            String paramValue = queryParamsMap.get(paramName);
            LOG.debug(paramName+"="+paramValue);
            try {
                Integer parsedValueI;
                Date parsedValueD;
                switch (paramName) {
                    case "id":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qOrdenCompra._id.eq(parsedValueI));
                        return bb;
                    case "fechaSolicitud":
                        paramValue = paramValue.trim();
                        parsedValueD = FormatoFechas.stringADateLocal(paramValue);
                        if (parsedValueD == null) {
                            LOG.warn("VentasService.queryParamsMapToVentasFilteringPredicate() : El formato de la fecha ingresada no es válida.");
                        } else {
                            bb.and(qOrdenCompra._fechaSolicitud.eq(parsedValueD));
                        }
                        break;
                    case "fechaRecepcion":
                        paramValue = paramValue.trim();
                        parsedValueD = FormatoFechas.stringADateLocal(paramValue);
                        if (parsedValueD == null) {
                            LOG.warn("VentasService.queryParamsMapToVentasFilteringPredicate() : El formato de la fecha ingresada no es válida.");
                        } else {
                            bb.and(qOrdenCompra._fechaRecepcion.eq(parsedValueD));
                        }
                        break;
                    case "empleadoId":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qOrdenCompra._empleado._id.eq(parsedValueI));
                        break;
                    case "empleado":
                        paramValue = "%" + paramValue + "%";
                        bb.and(qOrdenCompra._empleado._persona._nombreCompleto.likeIgnoreCase(paramValue));
                        break;
                    case "estado":
                        paramValue = paramValue.trim();
                        bb.and(qOrdenCompra._estado.eq(paramValue.charAt(0)));
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
    public int saveOrdenCompra(OrdenCompraDTO dto) throws NotFoundException {
        
        OrdenCompra entity = dto.toEntity();
        
        Optional<Empleado> empleadoEntity = empleadoRepo.findById(dto.getIdEmpleado());
        if (empleadoEntity.isPresent()) {
            entity.setEmpleado(empleadoEntity.get());
        } else {
            throw new NotFoundException("El empleado de la orden de compra no existe");
        }
        
        if (entity.getDetalles() == null || entity.getDetalles().isEmpty()) {
            return 0;
        } else {
            Iterator<DetalleOrdenCompra> it = entity.getDetalles().iterator();
            while (it.hasNext()) {
                DetalleOrdenCompra detalleEntity = it.next();
                Integer productoId = detalleEntity.getProducto().getId();
                Optional<Producto> productoEntityFromId = productoRepo.findById(productoId);
                LOG.debug("saveOrdenCompra - productoId="+productoId);
                if (productoEntityFromId.isPresent()) {
                    detalleEntity.setProducto(productoEntityFromId.get());
                } else {
                    throw new NotFoundException("Un producto listado en la orden de compra no existe");
                }
            }
            
            
            if (entity.getId() != null && entity.getId() != 0) {
                BooleanBuilder bb = new BooleanBuilder()
                        .and(QDetalleOrdenCompra.detalleOrdenCompra._ordenCompra._id.eq(entity.getId()));
                
                Iterable<DetalleOrdenCompra> itrbl = detalleOrdenCompraRepo.findAll(bb);
                detalleOrdenCompraRepo.deleteAll(itrbl);
            }
            
            entity = ordenCompraRepo.saveAndFlush(entity);
            return entity.getId();
        }
    }

    @Override
    public boolean deleteOrdenCompra(Integer ordenCompraId) {
        
        try {
            ordenCompraRepo.deleteById(ordenCompraId);
            return true;
        } catch (EmptyResultDataAccessException exc) {
            LOG.error("Error al borrar Orden de Compra con id " +ordenCompraId, exc);
        }
        return false;
    }
    
}
