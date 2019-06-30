package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.UsuarioDTO;
import cl.duoc.alumnos.ferme.util.FormatoFechas;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.NonNull;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Entity
@Table(name = "USUARIO")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_USUARIO")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "SEQ_USUARIO", initialValue = 1, allocationSize = FermeConfig.ESPACIO_ASIGNACION_SECUENCIAS_HIBERNATE)
    @GeneratedValue(generator = "usuario_seq", strategy = GenerationType.AUTO)
    private Integer _id;
    
    @NonNull
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA")
    @OneToOne(cascade = CascadeType.DETACH, optional = false, fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    private Persona _persona;
    
    @NonNull
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE")
    private String _nombre;
    
    @Size(min = 1, max = 100)
    @Column(name = "CLAVE")
    private String _clave;
    
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date _fechaCreacion;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "_usuario")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Sesion> _sesiones;

    public Usuario() {
        super();
    }

    public Integer getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getNombre() {
        return _nombre;
    }

    public void setNombre(String nombre) {
        this._nombre = nombre;
    }

    public String getClave() {
        return _clave;
    }

    public void setClave(String clave) {
        this._clave = clave;
    }

    public Date getFechaCreacion() {
        return _fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this._fechaCreacion = fechaCreacion;
    }

    public Persona getPersona() {
        return _persona;
    }

    public void setPersona(Persona persona) {
        this._persona = persona;
    }

    public List<Sesion> getSesiones() {
        return _sesiones;
    }
        
    public UsuarioDTO toDTO() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(_id);
        dto.setNombreUsuario(_nombre);
        
        String _fechaCreacion = FormatoFechas.dateAStringLocal(this._fechaCreacion);
        dto.setFechaCreacionUsuario(_fechaCreacion);
        
        Persona personaEntity = getPersona();
        dto.setIdPersona(personaEntity.getId());
        dto.setNombreCompletoPersona(personaEntity.getNombreCompleto());
        dto.setRutPersona(personaEntity.getRut());
        
        /*        
        
        String direccion = personaEntity.getDireccion();
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
        */
        
        
        return dto;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this._id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Usuario)) {
            return false;
        }
        final Usuario other = (Usuario) object;
        return (!Objects.equals(this._id, other.getId()));
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Usuario[ idUsuario=" + _id + " ]";
    }

}
