package cl.duoc.alumnos.ferme.services;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.SesionDTO;
import cl.duoc.alumnos.ferme.dto.UsuarioDTO;
import cl.duoc.alumnos.ferme.entities.Cargo;
import cl.duoc.alumnos.ferme.entities.Cliente;
import cl.duoc.alumnos.ferme.entities.Empleado;
import cl.duoc.alumnos.ferme.entities.Persona;
import cl.duoc.alumnos.ferme.entities.Sesion;
import cl.duoc.alumnos.ferme.entities.Usuario;
import cl.duoc.alumnos.ferme.jpa.repositories.IPersonasRepository;
import cl.duoc.alumnos.ferme.jpa.repositories.ISesionesRepository;
import cl.duoc.alumnos.ferme.services.interfaces.IPersonasService;
import cl.duoc.alumnos.ferme.services.interfaces.ISesionesService;
import cl.duoc.alumnos.ferme.util.FormatoFechas;
import cl.duoc.alumnos.ferme.util.Hashing;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Service
public class SesionesService implements ISesionesService {
    private Logger LOG = LoggerFactory.getLogger(SesionesService.class);

    @Autowired private IPersonasService personasSvc;
    @Autowired private IPersonasRepository personaRepo;
    @Autowired private ISesionesRepository sesionRepo;
    
    private final static long SESSION_LIFETIME = FermeConfig.DURACION_SESION;
    
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
        String fechaActual = FormatoFechas.dateAStringLocal(Calendar.getInstance().getTime());
        String sesionData = "[" + rutPersona + " conectado en " + fechaActual + "]";
        return sesionData;
    }

    private Empleado getOptionalEmpleadoFromUsuarioPersona(UsuarioDTO usuario) {
        LOG.debug("getOptionalEmpleadoFromUsuarioPersona");
        
        Integer idPersona = usuario.getIdPersona();
        Empleado foundEmpleado = personasSvc.getNullableEmpleadoFromIdPersona(idPersona);
        return foundEmpleado;
    }

    private Cliente getOptionalClienteFromUsuarioPersona(UsuarioDTO usuario) {
        LOG.debug("getOptionalClienteFromUsuarioPersona");
        
        Integer idPersona = usuario.getIdPersona();
        Cliente foundEmpleado = personasSvc.getNullableClienteFromIdPersona(idPersona);
        return foundEmpleado;
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
            String sesionHash = Hashing.encriptarStringSesion(sesionData);
            LOG.debug("abrirSesion - Asignando hash '"+sesionHash+"'");
            entity.setHash(sesionHash);
        } catch (NoSuchAlgorithmException exc) {
            throw new IllegalStateException("El algoritmo de encriptación configurado en la aplicación no es válido.", exc);
        }
        LOG.info("abrirSesion - Guardando sesion en la base de datos...");
        entity = sesionRepo.saveAndFlush(entity);
        LOG.debug("abrirSesion - Sesión creada y almacenada: "+entity.toString());
        
        SesionDTO dto = entity.toDTO();
        Empleado empleadoEntity = this.getOptionalEmpleadoFromUsuarioPersona(usuario);
        if (empleadoEntity != null) {
            LOG.debug("abrirSesion - Empleado id "+empleadoEntity.getId());
            Cargo cargoEntity = empleadoEntity.getCargo();
            dto.setIdEmpleado(empleadoEntity.getId());
            dto.setIdCargo(cargoEntity.getId());
        }
        
        Cliente clienteEntity = this.getOptionalClienteFromUsuarioPersona(usuario);
        if (clienteEntity != null) {
            LOG.debug("abrirSesion - Cliente id "+clienteEntity.getId());
            dto.setIdCliente(clienteEntity.getId());
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

	@Override
	public UsuarioDTO recuperarUsuarioDesdeHashSesion(String hashSesion) {
		Iterable<Sesion> sesiones = sesionRepo.findByHashWhereNotCerradas(hashSesion);
        for (Sesion ssn : sesiones) {
            Usuario usr = ssn.getUsuario();
            UsuarioDTO dto = usr.toDTO();
            Persona prs = personaRepo.findById(dto.getIdPersona()).get();
            
            String direccion = prs.getDireccion();
            String email = prs.getEmail();
            Long fono1 = prs.getFono1();
            Long fono2 = prs.getFono2();
            Long fono3 = prs.getFono3();
            
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
		return null;
	}
    
}
