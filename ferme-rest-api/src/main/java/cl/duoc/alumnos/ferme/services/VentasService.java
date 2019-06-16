package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.domain.entities.Cliente;
import cl.duoc.alumnos.ferme.domain.entities.DetalleVenta;
import cl.duoc.alumnos.ferme.domain.entities.Empleado;
import cl.duoc.alumnos.ferme.domain.entities.Producto;
import cl.duoc.alumnos.ferme.domain.entities.QVenta;
import cl.duoc.alumnos.ferme.domain.entities.Venta;
import cl.duoc.alumnos.ferme.domain.repositories.IClientesRepository;
import cl.duoc.alumnos.ferme.domain.repositories.IEmpleadosRepository;
import cl.duoc.alumnos.ferme.domain.repositories.IProductosRepository;
import cl.duoc.alumnos.ferme.domain.repositories.IVentasRepository;
import cl.duoc.alumnos.ferme.dto.DetalleVentaDTO;
import cl.duoc.alumnos.ferme.dto.VentaDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IVentasService;
import cl.duoc.alumnos.ferme.util.FermeDates;
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
 * @author got12
 */
@Service
@Transactional
public class VentasService implements IVentasService {

    @Autowired private IVentasRepository ventaRepo;
    @Autowired private IEmpleadosRepository empleadoRepo;
    @Autowired private IClientesRepository clienteRepo;
    @Autowired private IProductosRepository productoRepo;
    private static final Logger LOG = LoggerFactory.getLogger(VentasService.class);
    
    @Override
    public Collection<VentaDTO> getVentas(int pageSize, int pageIndex, Predicate condicion) {
        Sort orden = Sort.by(Ferme.VENTA_DEFAULT_SORT_COLUMN).ascending();
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
                        bb.and(qVentas.id.eq(parsedValueI));
                        return bb;
                    case "fecha":
                        paramValue = paramValue.trim();
                        Date fecha = FermeDates.fechaStringToDate(paramValue);
                        if (fecha == null) {
                            LOG.warn("queryParamsMapToVentasFilteringPredicate - El formato de la fecha ingresada no es válida.");
                        } else {
                            bb.and(qVentas.fecha.eq(fecha));
                        }
                        break;
                    case "cliente":
                        paramValue = "%" + paramValue.trim() + "%";
                        bb.and(qVentas.cliente.persona.nombreCompleto.like(paramValue));
                        break;
                    case "clienteId":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qVentas.cliente.id.eq(parsedValueI));
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
    public int saveVenta(VentaDTO dto) throws NotFoundException {
        
        Venta entity = dto.toEntity();
        
        Optional<Cliente> clienteEntity = clienteRepo.findById(dto.getIdCliente());
        if (clienteEntity.isPresent()) {
            entity.setCliente(clienteEntity.get());
        } else {
            throw new NotFoundException("El cliente de la venta no existe");
        }
        
        Optional<Empleado> empleadoEntity = empleadoRepo.findById(dto.getIdEmpleado());
        if (empleadoEntity.isPresent()) {
            entity.setEmpleado(empleadoEntity.get());
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
                    detalleEntity.setProducto(productoEntityFromId.get());
                } else {
                    throw new NotFoundException("Un producto listado en la venta no existe");
                }
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
