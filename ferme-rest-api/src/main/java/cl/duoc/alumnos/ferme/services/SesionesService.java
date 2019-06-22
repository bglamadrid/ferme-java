package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.domain.entities.Empleado;
import cl.duoc.alumnos.ferme.domain.entities.QEmpleado;
import cl.duoc.alumnos.ferme.domain.entities.Sesion;
import cl.duoc.alumnos.ferme.domain.entities.Usuario;
import cl.duoc.alumnos.ferme.dto.SesionDTO;
import cl.duoc.alumnos.ferme.dto.UsuarioDTO;
import cl.duoc.alumnos.ferme.domain.repositories.IEmpleadosRepository;
import cl.duoc.alumnos.ferme.domain.repositories.ISesionesRepository;
import cl.duoc.alumnos.ferme.services.interfaces.ISesionesService;
import cl.duoc.alumnos.ferme.util.FermeDates;
import cl.duoc.alumnos.ferme.util.FermeHashes;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 12g
 */
@Service
public class SesionesService implements ISesionesService {
    
    private Logger LOG = LoggerFactory.getLogger(SesionesService.class);

    @Autowired private IEmpleadosRepository empleadoRepo;
    @Autowired private ISesionesRepository sesionRepo;
    
    private final static long SESSION_DURATION = Ferme.SESSION_DURATION;
    
    @Override
    public Collection<SesionDTO> getSesiones(int pageSize, int pageIndex, Predicate condicion) 
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("No soportado.");
    }

    @Override
    public Predicate queryParamsMapToSesionesFilteringPredicate(Map<String, String> queryParamsMap) 
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("No soportado.");
    }

    private String constructSesionDataStringFromUsuarioDTO(UsuarioDTO usuario) {
        String rutPersona = usuario.getRutPersona();
        String fechaActual = FermeDates.fechaToString(Calendar.getInstance().getTime());
        String sesionData = "[" + rutPersona + " conectado en " + fechaActual + "]";
        return sesionData;
    }

    private Integer getIdCargoFromOptionalEmpleadoFromUsuarioPersona(UsuarioDTO usuario) {
        try {
            QEmpleado qEmp = QEmpleado.empleado;
            
            BooleanBuilder bb = new BooleanBuilder()
                    .and(qEmp._persona._id.eq(usuario.getIdPersona()));
            
            Optional<Empleado> foundEmpleado = empleadoRepo.findOne(bb);
            if (foundEmpleado.isPresent()) {
                return foundEmpleado.get().getCargo().getId();
            } else {
                return null;
            }
        } catch (Exception exc) {
            return null;
        }
    }
    
    @Override
    public SesionDTO abrirSesion(UsuarioDTO usuario) {
        Sesion entity = new Sesion();
        
        Usuario usuarioEntity = usuario.toEntity();
        entity.setUsuario(usuarioEntity);
        
        String sesionData = this.constructSesionDataStringFromUsuarioDTO(usuario);
        LOG.debug("abrirSesion - sesionData="+sesionData);
        try {
            String sesionHash = FermeHashes.encryptSessionData(sesionData);
            LOG.debug("abrirSesion - sesionHash="+sesionHash);
            entity.setHash(sesionHash);
        } catch (NoSuchAlgorithmException exc) {
            throw new IllegalStateException("El algoritmo de encriptación configurado en la aplicación no es válido.", exc);
        }
        
        entity = sesionRepo.saveAndFlush(entity);
        LOG.info("Sesión creada y almacenada: "+entity.toString());
        
        SesionDTO dto = entity.toDTO();
        Integer idCargo = this.getIdCargoFromOptionalEmpleadoFromUsuarioPersona(usuario);
        if (idCargo != null) {
            LOG.debug("El usuario asociado poseía un empleado con un cargo");
            dto.setIdCargo(idCargo);
        }
        
        return dto;
    }

    @Override
    public boolean validarSesion(SesionDTO sesion) {
        
        Calendar calAhora = Calendar.getInstance();
        Date ahora = calAhora.getTime();
        long alSerCreada = ahora.getTime() - (SESSION_DURATION * 1000);
        boolean unaVigente = false;
        
        Iterable<Sesion> sesiones = sesionRepo.findNotCerradaByHash(sesion.getHashSesion());
        int i = 0;
        for (Sesion ssn : sesiones) {
            if (alSerCreada > ssn.getAbierta().getTime() || unaVigente) {
                ssn.setCerrada(ahora);
                sesionRepo.save(ssn);
            } else {
                unaVigente = true;
            }
            i++;
        }
        LOG.info("validarSesion - Se encontraron "+i+" sesiones con el hash proveído");
        sesionRepo.flush();
        return false;
    }

    @Override
    public boolean cerrarSesion(SesionDTO sesion) {
        Calendar calAhora = Calendar.getInstance();
        Date ahora = calAhora.getTime();
        Iterable<Sesion> sesiones = sesionRepo.findNotCerradaByHash(sesion.getHashSesion());
        for (Sesion ssn : sesiones) {
            ssn.setCerrada(ahora);
            sesionRepo.save(ssn);
        }
        sesionRepo.flush();
        return true;
    }
    
}
