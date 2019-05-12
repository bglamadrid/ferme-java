package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.domain.entities.QVenta;
import cl.duoc.alumnos.ferme.domain.entities.Venta;
import cl.duoc.alumnos.ferme.domain.repositories.IVentasRepository;
import cl.duoc.alumnos.ferme.dto.VentaDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IVentasService;
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
public class VentasService implements IVentasService {

    @Autowired private IVentasRepository ventaRepo;
    private static final Logger LOG = LoggerFactory.getLogger(VentasService.class);
    
    @Override
    public Collection<VentaDTO> getVentas(int pageSize, int pageIndex, Predicate condicion) {
        Pageable pgbl = PageRequest.of(pageIndex, pageSize);
        
        List<VentaDTO> pagina = new ArrayList<>();
        Iterable<Venta> ventas;
        
        if (condicion == null) {
            ventas = ventaRepo.findAll(pgbl);
        } else {
            ventas = ventaRepo.findAll(condicion, pgbl);
        }
        
        ventas.forEach((entity) -> {
            VentaDTO dto = entity.toDTO();
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
                            LOG.warn("VentasService.queryParamsMapToVentasFilteringPredicate() : El formato de la fecha ingresada no es válida.");
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
                LOG.error("No se pudo traducir el parámetro '" + paramName + "' a un número (su valor era '" + paramValue + "').", exc);
            }
        }
        
        return bb;
    }

    @Override
    public int saveVenta(VentaDTO dto) {
        
        Venta entity = null;
        try {
            entity = dto.toEntity();
        } catch (ParseException ex) {
            LOG.error("La fecha de la venta ingresada tiene un formato incorrecto, debe ser: DD/MM/YYYY", ex);
        }
        if (entity != null) {
            entity = ventaRepo.saveAndFlush(entity);
            return entity.getId();
        } else {
            return 0;
        }
    }

    @Override
    public boolean deleteVenta(Integer ventaId) {
        
        try {
            ventaRepo.deleteById(ventaId);
            return true;
        } catch (EmptyResultDataAccessException exc) {
            LOG.error("Error al borrar Venta con id " +ventaId, exc);
        }
        return false;
    }
    
}
