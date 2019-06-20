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
    private Integer id;
    
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", insertable = true, updatable = true)
    @ManyToOne(cascade = CascadeType.DETACH, optional = false, fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    private Usuario usuario;
    
    @Size(min = 1, max = 1)
    @Column(name = "ABIERTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date abierta;
    
    @Size(min = 1, max = 1)
    @Column(name = "VIGENTE", columnDefinition = "CHAR")
    private String vigente;
    
    @Column(name = "HASH")
    private String hash;
    
    @Column(name = "CERRADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cerrada;

    public Sesion() {
        super();
    }

    public Sesion(Integer id) {
        super();
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getAbierta() {
        return abierta;
    }

    public void setAbierta(Date abierta) {
        this.abierta = abierta;
    }

    public String getVigente() {
        return vigente;
    }

    public void setVigente(String vigente) {
        this.vigente = vigente;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Date getCerrada() {
        return cerrada;
    }

    public void setCerrada(Date cerrada) {
        this.cerrada = cerrada;
    }

    public SesionDTO toDTO() {
        SesionDTO dto = new SesionDTO();
        Usuario usuarioEntity = this.usuario;
        
        dto.setIdUsuario(usuarioEntity.getId());
        dto.setNombreUsuario(usuarioEntity.getNombre());
        
        dto.setHashSesion(hash);
        dto.setVigenteSesion(vigente);
        
        DateFormat formateador = new SimpleDateFormat(Ferme.DEFAULT_DATE_FORMAT);
        dto.setAbiertaSesion(formateador.format(abierta));
        if (cerrada != null) { dto.setCerradaSesion(formateador.format(cerrada));  }
        
        return dto;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Sesion)) {
            return false;
        }
        final Sesion other = (Sesion) object;
        return (!Objects.equals(this.id, other.getId()));
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Sesion[ idUsuario=" + id + " ]";
    }
    
}
