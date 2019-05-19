package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.domain.entities.OrdenCompra;
import cl.duoc.alumnos.ferme.domain.entities.QOrdenCompra;
import cl.duoc.alumnos.ferme.domain.repositories.IOrdenesCompraRepository;
import cl.duoc.alumnos.ferme.dto.DetalleOrdenCompraDTO;
import cl.duoc.alumnos.ferme.dto.OrdenCompraDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IOrdenesCompraService;
import cl.duoc.alumnos.ferme.util.FermeDates;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
 * @author got12
 */
@Service
public class OrdenesCompraService implements IOrdenesCompraService {
    
    @Autowired private IOrdenesCompraRepository ordenCompraRepo;
    private static final Logger LOG = LoggerFactory.getLogger(OrdenesCompraService.class);

    @Override
    public Collection<OrdenCompraDTO> getOrdenesCompra(int pageSize, int pageIndex, Predicate condicion) {
        Pageable pgbl = PageRequest.of(pageIndex, pageSize);
        
        List<OrdenCompraDTO> pagina = new ArrayList<>();
        Iterable<OrdenCompra> ordenesCompra;
        
        if (condicion == null) {
            ordenesCompra = ordenCompraRepo.findAll(pgbl);
        } else {
            ordenesCompra = ordenCompraRepo.findAll(condicion, pgbl);
        }
        
        if (condicion != null && ordenCompraRepo.count(condicion) == 1) {
            ordenesCompra.forEach((entity) -> {
                OrdenCompraDTO dto = entity.toDTO(false);
                pagina.add(dto);
            });
        } else {
            ordenesCompra.forEach((entity) -> {
                OrdenCompraDTO dto = entity.toDTO(true);
                pagina.add(dto);
            });
        }
        
        
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
                        bb.and(qOrdenCompra.id.eq(parsedValueI));
                        return bb;
                    case "fechaSolicitud":
                        paramValue = paramValue.trim();
                        parsedValueD = FermeDates.fechaStringToDate(paramValue);
                        if (parsedValueD == null) {
                            LOG.warn("VentasService.queryParamsMapToVentasFilteringPredicate() : El formato de la fecha ingresada no es válida.");
                        } else {
                            bb.and(qOrdenCompra.fechaSolicitud.eq(parsedValueD));
                        }
                        break;
                    case "fechaRecepcion":
                        paramValue = paramValue.trim();
                        parsedValueD = FermeDates.fechaStringToDate(paramValue);
                        if (parsedValueD == null) {
                            LOG.warn("VentasService.queryParamsMapToVentasFilteringPredicate() : El formato de la fecha ingresada no es válida.");
                        } else {
                            bb.and(qOrdenCompra.fechaRecepcion.eq(parsedValueD));
                        }
                        break;
                    case "empleadoId":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qOrdenCompra.empleado.id.eq(parsedValueI));
                        break;
                    case "empleado":
                        bb.and(qOrdenCompra.empleado.persona.nombreCompleto.like(paramValue));
                        break;
                    case "estado":
                        paramValue = paramValue.trim();
                        bb.and(qOrdenCompra.estado.eq(paramValue.charAt(0)));
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
    public int saveOrdenCompra(OrdenCompraDTO dto) {
        
        
        OrdenCompra entity = null;
        try {
            entity = dto.toEntity();
        } catch (ParseException ex) {
            LOG.error("Una de las fechas ingresadas tiene un formato incorrecto, deben ser: DD/MM/YYYY", ex);
        }
        
        if (entity == null) {
            return 0;
        } else if (entity.getDetalles() == null || entity.getDetalles().isEmpty()) {
            return 0;
        } else {
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
