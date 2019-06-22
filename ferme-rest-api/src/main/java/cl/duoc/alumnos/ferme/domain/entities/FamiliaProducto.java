package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.dto.FamiliaProductoDTO;
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
@Table(name = "FAMILIA_PRODUCTO")
@NamedQueries({
    @NamedQuery(name = "FamiliaProducto.findAll", query = "SELECT f FROM FamiliaProducto f")})
public class FamiliaProducto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_FAMILIA_PRODUCTO")
    @SequenceGenerator(name = "familia_producto_seq", sequenceName = "SEQ_FAMILIA_PRODUCTO", initialValue = 1, allocationSize = Ferme.DEFAULT_HIBERNATE_SEQUENCES_ALLOCATION_SIZE)
    @GeneratedValue(generator = "familia_producto_seq", strategy = GenerationType.AUTO)
    private Integer _id;
    
    @Size(min = 1, max = 100)
    @Column(name = "DESCRIPCION")
    private String _descripcion;
    
    @JoinColumn(name = "ID_RUBRO", referencedColumnName = "ID_RUBRO")
    @ManyToOne(cascade = CascadeType.DETACH, optional = false, fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    private Rubro _rubro;
    
    @JoinColumn(name = "ID_PROVEEDOR", referencedColumnName = "ID_PROVEEDOR")
    @ManyToOne(cascade = CascadeType.DETACH, optional = false, fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    private Proveedor _proveedor;

    public FamiliaProducto() {
        super();
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        this._id = id;
    }

    public String getDescripcion() {
        return _descripcion;
    }

    public void setDescripcion(String descripcion) {
        this._descripcion = descripcion;
    }

    public Rubro getRubro() {
        return _rubro;
    }

    public void setRubro(Rubro rubro) {
        this._rubro = rubro;
    }

    public Proveedor getProveedor() {
        return _proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this._proveedor = proveedor;
    }

    public FamiliaProductoDTO toDTO() {
        FamiliaProductoDTO dto = new FamiliaProductoDTO();
        
        dto.setIdFamiliaProducto(_id);
        dto.setIdRubro(_rubro.getId());
        dto.setIdProveedor(_proveedor.getId());
        dto.setDescripcionFamiliaProducto(_descripcion);
        dto.setDescripcionRubro(_rubro.getDescripcion());
        
        return dto;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this._id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FamiliaProducto)) {
            return false;
        }
        final FamiliaProducto other = (FamiliaProducto) object;
        return (Objects.equals(this._id, other.getId()));
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.FamiliaProducto[ id=" + _id + " ]";
    }
    
}
