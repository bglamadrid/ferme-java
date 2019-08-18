package cl.duoc.alumnos.ferme.entities;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;

import cl.duoc.alumnos.ferme.Globals;
import cl.duoc.alumnos.ferme.dto.ProveedorDTO;

/** Representa un registro de la tabla PROVEEDOR.
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Entity
@Table(name = "PROVEEDOR")
@NamedQueries({
    @NamedQuery(name = "Proveedor.findAll", query = "SELECT p FROM Proveedor p")})
public class Proveedor implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_PROVEEDOR")
    @SequenceGenerator(name = "proveedor_seq", sequenceName = "SEQ_PROVEEDOR", initialValue = 1, allocationSize = Globals.ESPACIO_ASIGNACION_SECUENCIAS_HIBERNATE)
    @GeneratedValue(generator = "proveedor_seq", strategy = GenerationType.AUTO)
    private Integer _id;
    
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA")
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Persona _persona;
    
    @Size(min = 1, max = 50)
    @Column(name = "RAZON_SOCIAL")
    private String _razonSocial;

    public Proveedor() {
        super();
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        this._id = id;
    }

    public String getRazonSocial() {
        return _razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this._razonSocial = razonSocial;
    }

    public Persona getPersona() {
        return _persona;
    }

    public void setPersona(Persona persona) {
        this._persona = persona;
    }

    public ProveedorDTO toDTO() {
        ProveedorDTO dto = _persona.toProveedorDTO();
        dto.setIdProveedor(_id);
        dto.setRazonSocialProveedor(_razonSocial);
        
        return dto;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this._id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Proveedor)) {
            return false;
        }
        final Proveedor other = (Proveedor) object;
        return (Objects.equals(this._id, other.getId()));
    }
    
    

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Proveedor[ idProveedor=" + _id + " ]";
    }
    
}
