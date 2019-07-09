package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.domain.entities.Cliente;
import cl.duoc.alumnos.ferme.domain.entities.Empleado;
import cl.duoc.alumnos.ferme.domain.entities.Persona;
import cl.duoc.alumnos.ferme.domain.entities.Proveedor;
import cl.duoc.alumnos.ferme.domain.entities.QCliente;
import cl.duoc.alumnos.ferme.domain.entities.QEmpleado;
import cl.duoc.alumnos.ferme.domain.entities.QPersona;
import cl.duoc.alumnos.ferme.domain.entities.QProveedor;
import cl.duoc.alumnos.ferme.domain.repositories.IClientesRepository;
import cl.duoc.alumnos.ferme.domain.repositories.IEmpleadosRepository;
import cl.duoc.alumnos.ferme.domain.repositories.IPersonasRepository;
import cl.duoc.alumnos.ferme.domain.repositories.IProveedoresRepository;
import cl.duoc.alumnos.ferme.dto.PersonaDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IPersonasService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Service
public class PersonasService implements IPersonasService {
    private final static Logger LOG = LoggerFactory.getLogger(PersonasService.class);
    
    @Autowired IPersonasRepository personaRepo;
    @Autowired IEmpleadosRepository empleadoRepo;
    @Autowired IClientesRepository clienteRepo;
    @Autowired IProveedoresRepository proveedorRepo;

    public static final <T extends PersonaDTO> T cargarDatosPersonaEnDTO(Persona personaEntity, T dto) {
        dto.setIdPersona(personaEntity.getId());
        dto.setNombreCompletoPersona(personaEntity.getNombreCompleto());
        dto.setRutPersona(personaEntity.getRut());
        String direccion = personaEntity.getDireccion();
        String email = personaEntity.getEmail();
        Long fono1 = personaEntity.getFono1();
        Long fono2 = personaEntity.getFono2();
        Long fono3 = personaEntity.getFono3();
        if (direccion != null) {
            dto.setDireccionPersona(direccion);
        }
        if (email != null) {
            dto.setEmailPersona(email);
        }
        if (fono1 != null) {
            dto.setFonoPersona1(fono1);
        }
        if (fono2 != null) {
            dto.setFonoPersona2(fono2);
        }
        if (fono3 != null) {
            dto.setFonoPersona3(fono3);
        }
        return dto;
    }

    @Override
    public Collection<PersonaDTO> getPersonas(int pageSize, int pageIndex, Predicate condicion) {
        List<PersonaDTO> pagina = new ArrayList<>();
        Iterable<Persona> pesonas;
        long pesonaCount;
        
        LOG.info("getPersonas - Procesando solicitud...");
        Sort orden = Sort.by(FermeConfig.COLUMNAS_ORDENAMIENTO_MAPA.get(Persona.class)).ascending();
        Pageable pgbl = PageRequest.of(pageIndex, pageSize, orden);
        
        LOG.info("getPersonas - Llamando queries...");
        if (condicion == null) {
            pesonas = personaRepo.findAll(pgbl);
            pesonaCount = personaRepo.count();
        } else {
            pesonas = personaRepo.findAll(condicion, pgbl);
            pesonaCount = personaRepo.count(condicion);
        }
        LOG.info("getPersonas - Se han encontrado "+pesonaCount+" personas con los filtros ingresados.");
        
        LOG.info("getPersonas - Procesando resultados...");
        pesonas.forEach((entity) -> {
            PersonaDTO dto = entity.toDTO();
            pagina.add(dto);
        });
        LOG.info("getPersonas - Resultados procesados con éxito.");
        
        return pagina;
    }

    @Override
    public Predicate queryParamsMapToPersonasFilteringPredicate(Map<String, String> queryParamsMap) {
        
        QPersona qPersona = QPersona.persona;
        BooleanBuilder bb = new BooleanBuilder();
        for (String paramName : queryParamsMap.keySet()) {
            String paramValue = queryParamsMap.get(paramName);
            LOG.debug(paramName+"="+paramValue);
            try {
                Integer parsedValueI;
                switch (paramName) {
                    case "id":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qPersona._id.eq(parsedValueI));
                        return bb; //match por id es único
                    case "nombre":
                        paramValue = "%" + paramValue + "%";
                        bb.and(qPersona._nombreCompleto.likeIgnoreCase(paramValue));
                        break;
                    case "rut":
                        paramValue = "%" + paramValue + "%";
                        bb.and(qPersona._rut.likeIgnoreCase(paramValue));
                        break;
                    default: break;
                }
            } catch (NumberFormatException exc) {
                LOG.error("No se pudo traducir el parámetro '" + paramName + "' a un número (su valor era '" + paramValue + "').", exc);
            }
        }
        
        return bb;
    }
    
    

    public Empleado getNullableEmpleadoFromIdPersona(Integer idPersona) {
        LOG.debug("getOptionalEmpleadoFromIdPersona");
        try {
            QEmpleado qEmp = QEmpleado.empleado;
            
            BooleanBuilder bb = new BooleanBuilder()
                    .and(qEmp._persona._id.eq(idPersona));
            
            LOG.debug("getOptionalEmpleadoFromIdPersona - bb="+bb.toString());
            Optional<Empleado> foundEmpleado = empleadoRepo.findOne(bb);
            if (foundEmpleado.isPresent()) {
                return foundEmpleado.get();
            } else {
                return null;
            }
        } catch (Exception exc) {
            return null;
        }
    }
    
    public Cliente getNullableClienteFromIdPersona(Integer idPersona) {
        LOG.debug("getOptionalClienteFromIdPersona");
        try {
            QCliente qCli = QCliente.cliente;
            
            BooleanBuilder bb = new BooleanBuilder()
                    .and(qCli._persona._id.eq(idPersona));
            
            LOG.debug("getOptionalClienteFromIdPersona - bb="+bb.toString());
            Optional<Cliente> foundCliente = clienteRepo.findOne(bb);
            if (foundCliente.isPresent()) {
                return foundCliente.get();
            } else {
                return null;
            }
        } catch (Exception exc) {
            return null;
        }
    }
    
    public Proveedor getNullableProveedorFromIdPersona(Integer idPersona) {
        LOG.debug("getOptionalProveedorFromIdPersona");
        try {
            QProveedor qProv = QProveedor.proveedor;
            
            BooleanBuilder bb = new BooleanBuilder()
                    .and(qProv._persona._id.eq(idPersona));
            
            LOG.debug("getOptionalProveedorFromIdPersona - bb="+bb.toString());
            Optional<Proveedor> foundProveedor = proveedorRepo.findOne(bb);
            if (foundProveedor.isPresent()) {
                return foundProveedor.get();
            } else {
                return null;
            }
        } catch (Exception exc) {
            return null;
        }
    }
    
}
