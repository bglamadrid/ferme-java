package cl.duoc.alumnos.ferme.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.DetalleVentaDTO;
import cl.duoc.alumnos.ferme.dto.VentaDTO;
import cl.duoc.alumnos.ferme.entities.Cliente;
import cl.duoc.alumnos.ferme.entities.DetalleVenta;
import cl.duoc.alumnos.ferme.entities.Empleado;
import cl.duoc.alumnos.ferme.entities.Producto;
import cl.duoc.alumnos.ferme.entities.QDetalleVenta;
import cl.duoc.alumnos.ferme.entities.QVenta;
import cl.duoc.alumnos.ferme.entities.Venta;
import cl.duoc.alumnos.ferme.jpa.repositories.IClientesRepository;
import cl.duoc.alumnos.ferme.jpa.repositories.IDetallesVentasRepository;
import cl.duoc.alumnos.ferme.jpa.repositories.IEmpleadosRepository;
import cl.duoc.alumnos.ferme.jpa.repositories.IProductosRepository;
import cl.duoc.alumnos.ferme.jpa.repositories.IVentasRepository;
import cl.duoc.alumnos.ferme.services.interfaces.IVentasService;
import cl.duoc.alumnos.ferme.util.FormatoFechas;
import javassist.NotFoundException;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Service
@Transactional
public class VentasService implements IVentasService {
    private static final Logger LOG = LoggerFactory.getLogger(VentasService.class);

    @Autowired private IVentasRepository ventaRepo;
    @Autowired private IDetallesVentasRepository detalleVentaRepo;
    @Autowired private IEmpleadosRepository empleadoRepo;
    @Autowired private IClientesRepository clienteRepo;
    @Autowired private IProductosRepository productoRepo;

    @Override
    public Integer getNextId() {
        Sort idSort = Sort.by(Sort.Direction.DESC, "_id");
        Iterator<Venta> it = ventaRepo.findAll(idSort).iterator();
        if (it.hasNext()){
            return it.next().getId() +1 ;
        } else {
            return 1;
        }
    }
    
    @Override
    public Collection<VentaDTO> getVentas(int pageSize, int pageIndex, Predicate condicion) {
        Sort orden = Sort.by(FermeConfig.COLUMNAS_ORDENAMIENTO_MAPA.get(Venta.class)).ascending();
        Pageable pgbl = PageRequest.of(pageIndex, pageSize, orden);
        
        List<VentaDTO> pagina = new ArrayList<>();
        Iterable<Venta> ventas;
        
        if (condicion == null) {
            ventas = ventaRepo.findAll(pgbl);
        } else {
            ventas = ventaRepo.findAll(condicion, pgbl);
        }
        
        ventas.forEach((entity) -> {
            VentaDTO dto = entity.toDTO(true);
            pagina.add(dto);
        });
        
        
        return pagina;
    }

    @Override
    public Collection<DetalleVentaDTO> getDetallesVenta(Integer ventaId) {
        
        Venta entity = null;
        try {
            entity = ventaRepo.getOne(ventaId);
        } catch (Exception e) {
            LOG.error("getDetallesVenta - No se encontró una venta con el ID " + ventaId, e);
        }
        
        if (entity == null) {
            return null;
        } else {
            VentaDTO dtoReal = entity.toDTO(false);
            return dtoReal.getDetallesVenta();
        }
    }

    @Override
    public Predicate queryParamsMapToVentasFilteringPredicate(Map<String, String> queryParamsMap) {
        
        QVenta qVentas = QVenta.venta;
        BooleanBuilder bb = new BooleanBuilder();
        for (String paramName : queryParamsMap.keySet()) {
            String paramValue = queryParamsMap.get(paramName);
            LOG.debug(paramName+"="+paramValue);
            try {
                Integer parsedValueI;
                switch (paramName) {
                    case "id":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qVentas._id.eq(parsedValueI));
                        return bb;
                    case "fecha":
                        paramValue = paramValue.trim();
                        Date fecha = FormatoFechas.stringADateLocal(paramValue);
                        if (fecha == null) {
                            LOG.warn("queryParamsMapToVentasFilteringPredicate - El formato de la fecha ingresada no es válida.");
                        } else {
                            bb.and(qVentas._fecha.eq(fecha));
                        }
                        break;
                    case "cliente":
                        paramValue = "%" + paramValue.trim() + "%";
                        bb.and(qVentas._cliente._persona._nombreCompleto.likeIgnoreCase(paramValue));
                        break;
                    case "clienteId":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qVentas._cliente._id.eq(parsedValueI));
                        break;
                    default: break;
                }
            } catch (NumberFormatException exc) {
                LOG.error("queryParamsMapToVentasFilteringPredicate - No se pudo traducir el parámetro '" + paramName + "' a un número (su valor era '" + paramValue + "').", exc);
            }
        }
        
        return bb;
    }

    @Override
    public int saveVenta(VentaDTO dto) throws NotFoundException {
        
        Venta entity = dto.toEntity();
        LOG.debug("saveVenta - entiy="+entity.toString());
        
        Optional<Cliente> clienteEntity = clienteRepo.findById(dto.getIdCliente());
        if (clienteEntity.isPresent()) {
            entity.setCliente(clienteEntity.get());
        } else {
            throw new NotFoundException("El cliente de la venta no existe");
        }
        
        if (dto.getIdEmpleado() != null) {
            Optional<Empleado> empleadoEntity = empleadoRepo.findById(dto.getIdEmpleado());
            if (empleadoEntity.isPresent()) {
                entity.setEmpleado(empleadoEntity.get());
            }
        }
        
        if (entity.getDetalles() == null || entity.getDetalles().isEmpty()) {
            return 0;
        } else {
            Iterator<DetalleVenta> it = entity.getDetalles().iterator();
            while (it.hasNext()) {
                DetalleVenta detalleEntity = it.next();
                Integer productoId = detalleEntity.getProducto().getId();
                Optional<Producto> productoEntityFromId = productoRepo.findById(productoId);
                LOG.debug("saveVenta - productoId="+productoId);
                if (productoEntityFromId.isPresent()) {
                    Producto productoEntity = productoEntityFromId.get();
                    detalleEntity.setProducto(productoEntity);
                    Long montoDetalle = productoEntity.getPrecio() * detalleEntity.getUnidades();
                    detalleEntity.setMonto(montoDetalle.intValue());    
                } else {
                    throw new NotFoundException("Un producto listado en la venta no existe");
                }
            }
            
            if (entity.getId() != null && entity.getId() != 0) {
                BooleanBuilder bb = new BooleanBuilder()
                        .and(QDetalleVenta.detalleVenta._venta._id.eq(entity.getId()));
                
                Iterable<DetalleVenta> itrbl = detalleVentaRepo.findAll(bb);
                detalleVentaRepo.deleteAll(itrbl);
            }
            
            entity = ventaRepo.saveAndFlush(entity);
            return entity.getId();
        }
    }

    @Override
    public boolean deleteVenta(Integer ventaId) {
        
        try {
            ventaRepo.deleteById(ventaId);
            return true;
        } catch (EmptyResultDataAccessException exc) {
            LOG.error("deleteVenta - Error al borrar Venta con id " +ventaId, exc);
        }
        return false;
    }
    
}
