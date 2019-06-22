package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.domain.entities.Persona;
import cl.duoc.alumnos.ferme.domain.entities.Proveedor;
import cl.duoc.alumnos.ferme.domain.entities.QProveedor;
import cl.duoc.alumnos.ferme.domain.repositories.IPersonasRepository;
import cl.duoc.alumnos.ferme.domain.repositories.IProveedoresRepository;
import cl.duoc.alumnos.ferme.dto.ProveedorDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IProveedoresService;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author got12
 */
@Service
@Transactional
public class ProveedoresService implements IProveedoresService {
    
    @Autowired IProveedoresRepository proveedorRepo;
    @Autowired IPersonasRepository personaRepo;
    private final static Logger LOG = LoggerFactory.getLogger(ProductosService.class);

    @Override
    public Collection<ProveedorDTO> getProveedores(int pageSize, int pageIndex, Predicate condicion) {
        List<ProveedorDTO> pagina = new ArrayList<>();
        Iterable<Proveedor> proveedores;
        long proveedorCount;
        
        LOG.info("getProveedores - Procesando solicitud...");
        Sort orden = Sort.by(FermeConfig.PROVEEDOR_DEFAULT_SORT_COLUMN).ascending();
        Pageable pgbl = PageRequest.of(pageIndex, pageSize, orden);
        
        LOG.info("getProveedores - Llamando queries...");
        if (condicion == null) {
            proveedores = proveedorRepo.findAll(pgbl);
            proveedorCount = proveedorRepo.count();
        } else {
            proveedores = proveedorRepo.findAll(condicion, pgbl);
            proveedorCount = proveedorRepo.count(condicion);
        }
        LOG.info("getProveedores - Se han encontrado "+proveedorCount+" proveedores con los filtros ingresados.");
        
        LOG.info("getProveedores - Procesando resultados...");
        proveedores.forEach((entity) -> {
            ProveedorDTO dto = entity.toDTO();
            pagina.add(dto);
        });
        LOG.info("getProveedores - Resultados procesados con éxito.");
        
        return pagina;
    }

    @Override
    public Predicate queryParamsMapToProveedoresFilteringPredicate(Map<String, String> queryParamsMap) {
        
        QProveedor qProveedor = QProveedor.proveedor;
        BooleanBuilder bb = new BooleanBuilder();
        for (String paramName : queryParamsMap.keySet()) {
            String paramValue = queryParamsMap.get(paramName);
            LOG.debug(paramName+"="+paramValue);
            try {
                Integer parsedValueI;
                switch (paramName) {
                    case "id":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qProveedor._id.eq(parsedValueI));
                        return bb; //match por id es único
                    case "personaId":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qProveedor._persona._id.eq(parsedValueI));
                        return bb; //match por id de persona es único
                    case "nombre":
                        paramValue = "%" + paramValue.toUpperCase() + "%";
                        bb.and(qProveedor._persona._nombreCompleto.upper().like(paramValue));
                        break;
                    case "rut":
                        paramValue = "%" + paramValue.toUpperCase() + "%";
                        bb.and(qProveedor._persona._rut.upper().like(paramValue));
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
    public int saveProveedor(ProveedorDTO dto) {
        
        Proveedor entity = dto.toEntity();
        Persona personaEntity = entity.getPersona();
        personaEntity = personaRepo.saveAndFlush(personaEntity);
        entity.setPersona(personaEntity);
        entity = proveedorRepo.saveAndFlush(entity);
        return entity.getId();
    }
    

    @Override
    public boolean deleteProveedor(Integer proveedorid) {
        
        try {
            proveedorRepo.deleteById(proveedorid);
            return true;
        } catch (EmptyResultDataAccessException exc) {
            LOG.error("Error al borrar Proveedor con id " +proveedorid, exc);
        }
        return false;
    }

    
    
}
