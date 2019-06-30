package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.domain.entities.Cargo;
import cl.duoc.alumnos.ferme.domain.entities.Producto;
import cl.duoc.alumnos.ferme.domain.entities.QCargo;
import cl.duoc.alumnos.ferme.domain.repositories.ICargosRepository;
import cl.duoc.alumnos.ferme.domain.repositories.IProductosRepository;
import cl.duoc.alumnos.ferme.domain.repositories.IReportesRepository;
import cl.duoc.alumnos.ferme.dto.CargoDTO;
import cl.duoc.alumnos.ferme.services.interfaces.ICargosService;
import cl.duoc.alumnos.ferme.services.interfaces.IReportesService;
import cl.duoc.alumnos.ferme.util.FormatoFechas;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Service
public class ReportesService implements IReportesService {    
    
    @Autowired IReportesRepository reportesRepo;
    @Autowired IProductosRepository productosRepo;

    @Override
    public Long obtenerTotalVentasRealizadas(Map<String,String> allRequestParams) {
        if (allRequestParams.isEmpty()) {
            return reportesRepo.findVentasRealizadas();
        } else {
            Date fechaInicio = null;
            Date fechaFin = null;
            
            if (allRequestParams.containsKey("inicio")) {
                String sFechaInicio = allRequestParams.get("inicio");
                fechaInicio = FormatoFechas.stringADateLocal(sFechaInicio);   
            }
            
            if (allRequestParams.containsKey("fin")) {
                String sFechaFin = allRequestParams.get("fin");
                fechaFin = FormatoFechas.stringADateLocal(sFechaFin);
            }
            
            return reportesRepo.findVentasRealizadas(fechaInicio, fechaFin);
        }
    }

    @Override
    public Long obtenerUnidadesProductoVendidas(Map<String, String> allRequestParams) {
        /*if (allRequestParams.isEmpty()) {
            return null;
        } else {
            Producto prod = null;
            Date fechaInicio = null;
            Date fechaFin = null;
            
            if (allRequestParams.containsKey("idProducto")) {
                Integer idProducto = Integer.valueOf(allRequestParams.get("idProducto"));
                Optional<Producto> prodQuery = productosRepo.findById(idProducto);
                if (prodQuery.isPresent()) {
                    prod = prodQuery.get();
                } else {
                    return null;
                }
            } else {
                return null;
            }
            
            if (allRequestParams.containsKey("inicio")) {
                String sFechaInicio = allRequestParams.get("inicio");
                fechaInicio = FermeDates.fechaStringToDate(sFechaInicio);   
            }
            
            if (fechaInicio != null && allRequestParams.containsKey("fin")) {
                String sFechaFin = allRequestParams.get("fin");
                fechaFin = FermeDates.fechaStringToDate(sFechaFin);
            }
            
            if (fechaInicio != null && fechaFin != null) {
                return reportesRepo.findUnidadesDeProductoVendidas(prod, fechaInicio, fechaFin);
            } else if (fechaInicio != null) {
                return reportesRepo.findUnidadesDeProductoVendidas(prod, fechaInicio);
            } else {
                return reportesRepo.findUnidadesDeProductoVendidas(prod);
            }
        }*/
        return 0L;
    }
    
    
    
}
