package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.dto.SesionDTO;
import cl.duoc.alumnos.ferme.dto.UsuarioDTO;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cascade;

/**
 *
 * @author Benjamin Guillermo
 */
@Entity
@Table(name = "SESION")
@NamedQueries({
    @NamedQuery(name = "Sesion.findAll", query = "SELECT u FROM Sesion u")})
public class Sesion implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_SESION")
    @SequenceGenerator(name = "sesion_seq", sequenceName = "SEQ_SESION", initialValue = 1, allocationSize = Ferme.DEFAULT_HIBERNATE_SEQUENCES_ALLOCATION_SIZE)
    @GeneratedValue(generator = "sesion_seq", strategy = GenerationType.AUTO)
    private Integer _id;
    
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", insertable = true, updatable = true)
    @ManyToOne(cascade = CascadeType.DETACH, optional = false, fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    private Usuario _usuario;
    
    @Size(min = 1, max = 1)
    @Column(name = "ABIERTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date _abierta;
    
    @Size(min = 1, max = 1)
    @Column(name = "VIGENTE", columnDefinition = "CHAR")
    private String _vigente;
    
    @Column(name = "HASH")
    private String _hash;
    
    @Column(name = "CERRADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date _cerrada;

    public Sesion() {
        super();
    }

    public Sesion(Integer id) {
        super();
        this._id = id;
    }

    public Integer getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public Usuario getUsuario() {
        return _usuario;
    }

    public void setUsuario(Usuario usuario) {
        this._usuario = usuario;
    }

    public Date getAbierta() {
        return _abierta;
    }

    public void setAbierta(Date abierta) {
        this._abierta = abierta;
    }

    public String getVigente() {
        return _vigente;
    }

    public void setVigente(String vigente) {
        this._vigente = vigente;
    }

    public String getHash() {
        return _hash;
    }

    public void setHash(String hash) {
        this._hash = hash;
    }

    public Date getCerrada() {
        return _cerrada;
    }

    public void setCerrada(Date cerrada) {
        this._cerrada = cerrada;
    }

    public SesionDTO toDTO() {
        SesionDTO dto = new SesionDTO();
        Usuario usuarioEntity = getUsuario();
        
        dto.setHashSesion(_hash);
        dto.setIdUsuario(usuarioEntity.getId());
        dto.setNombreUsuario(usuarioEntity.getNombre());
        
        
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
        if (!(object instanceof Sesion)) {
            return false;
        }
        final Sesion other = (Sesion) object;
        return (!Objects.equals(this._id, other.getId()));
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Sesion[ idUsuario=" + _id + " ]";
    }
    
}
