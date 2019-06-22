package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.dto.TipoProductoDTO;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cascade;

/**
 *
 * @author Benjamin Guillermo
 */
@Entity
@Table(name = "TIPO_PRODUCTO")
@NamedQueries({
    @NamedQuery(name = "TipoProducto.findAll", query = "SELECT t FROM TipoProducto t")})
public class TipoProducto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_TIPO_PRODUCTO")
    @SequenceGenerator(name = "tipo_producto_seq", sequenceName = "SEQ_TIPO_PRODUCTO", initialValue = 1, allocationSize = Ferme.DEFAULT_HIBERNATE_SEQUENCES_ALLOCATION_SIZE)
    @GeneratedValue(generator = "tipo_producto_seq", strategy = GenerationType.AUTO)
    private Integer _id;
    
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE_TIPO")
    private String _nombre;
    
    @JoinColumn(name = "ID_FAMILIA_PRODUCTO", referencedColumnName = "ID_FAMILIA_PRODUCTO")
    @ManyToOne(cascade = CascadeType.DETACH, optional = false, fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    private FamiliaProducto _familia;

    public TipoProducto() {
        super();
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        this._id = id;
    }

    public String getNombre() {
        return _nombre;
    }

    public void setNombre(String nombre) {
        this._nombre = nombre;
    }

    public FamiliaProducto getFamilia() {
        return _familia;
    }

    public void setFamilia(FamiliaProducto familia) {
        this._familia = familia;
    }

    public TipoProductoDTO toDTO() {
        TipoProductoDTO dto = new TipoProductoDTO();
        dto.setIdTipoProducto(_id);
        dto.setNombreTipoProducto(_nombre);
        
        FamiliaProducto fml = getFamilia();
        dto.setIdFamiliaProducto(fml.getId());
        dto.setNombreFamiliaProducto(fml.getDescripcion());
        
        return dto;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this._id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TipoProducto)) {
            return false;
        }
        final TipoProducto other = (TipoProducto) object;
        return (Objects.equals(this._id, other.getId()));
    }
    
    

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.TipoProducto[ idTipoProducto=" + _id + " ]";
    }
    
}
