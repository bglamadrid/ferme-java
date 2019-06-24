package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.FermeConfig;
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
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Service
public class SesionesService implements ISesionesService {
    
    private Logger LOG = LoggerFactory.getLogger(SesionesService.class);

    @Autowired private IEmpleadosRepository empleadoRepo;
    @Autowired private ISesionesRepository sesionRepo;
    
    private final static long SESSION_LIFETIME = FermeConfig.SESSION_DURATION;
    
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
        LOG.info("abrirSesion - Generando datos de sesion...");
        Sesion entity = new Sesion();
        LOG.debug("abrirSesion - Asignando usuario...");
        Usuario usuarioEntity = usuario.toEntity();
        entity.setUsuario(usuarioEntity);
        LOG.debug("abrirSesion - Encriptando identificador de sesion...");
        String sesionData = this.constructSesionDataStringFromUsuarioDTO(usuario);
        LOG.debug("abrirSesion - sesionData="+sesionData);
        try {
            String sesionHash = FermeHashes.encryptSessionData(sesionData);
            LOG.debug("abrirSesion - Asignando hash '"+sesionHash+"'");
            entity.setHash(sesionHash);
        } catch (NoSuchAlgorithmException exc) {
            throw new IllegalStateException("El algoritmo de encriptación configurado en la aplicación no es válido.", exc);
        }
        LOG.info("abrirSesion - Guardando sesion en la base de datos...");
        entity = sesionRepo.saveAndFlush(entity);
        LOG.debug("Sesión creada y almacenada: "+entity.toString());
        
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
        long msFechaActual = ahora.getTime();
        boolean yaHayUnaVigente = false;
        
        Iterable<Sesion> sesiones = sesionRepo.findByHashWhereNotCerradas(sesion.getHashSesion());
        int i = 0;
        for (Sesion ssn : sesiones) {
            
            // para que la sesion 'ssn' esté vigente, debe haber pasado menos tiempo que SESSION_LIFETIME desde su creacion
            long msEstaFechaCreacion = ssn.getAbierta().getTime();
            long msEstaFechaExpiracion = msEstaFechaCreacion + SESSION_LIFETIME;
            
            boolean estaSesionVigente = (msEstaFechaExpiracion < msFechaActual);
            if (estaSesionVigente) {
                ssn.setCerrada(ahora);
                sesionRepo.save(ssn);
            } else {
                if (!yaHayUnaVigente) {
                    yaHayUnaVigente = true;
                } else {
                    ssn.setCerrada(ahora);
                    sesionRepo.save(ssn);
                }
            }
            i++;
        }
        LOG.info("validarSesion - Se encontraron "+i+" sesiones con el hash proveído");
        sesionRepo.flush();
        return yaHayUnaVigente;
    }

    @Override
    public boolean cerrarSesiones(SesionDTO sesion) {
        Date fechaAhora = Calendar.getInstance().getTime();
        String hashSesion = sesion.getHashSesion();
        Iterable<Sesion> sesiones = sesionRepo.findByHashWhereNotCerradas(hashSesion);
        for (Sesion ssn : sesiones) {
            ssn.setCerrada(fechaAhora);
            sesionRepo.save(ssn);
        }
        sesionRepo.flush();
        return true;
    }
    
}
