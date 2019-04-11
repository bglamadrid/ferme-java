package cl.duoc.alumnos.ferme.domain.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

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
    private int idFamiliaProducto;
    
    @Size(min = 1, max = 100)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "ID_PROVEEDOR")
    @OneToOne(optional = false)
    private Proveedor proveedor;
    
    @JoinColumn(name = "ID_RUBRO", referencedColumnName = "ID_RUBRO")
    @ManyToOne(optional = false)
    private Rubro rubro;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "familiaProducto")
    private List<TipoProducto> tiposProductos;

    public FamiliaProducto() {
    }

    public int getIdFamiliaProducto() {
        return idFamiliaProducto;
    }

    public void setIdFamiliaProducto(int idFamiliaProducto) {
        this.idFamiliaProducto = idFamiliaProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }

    public List<TipoProducto> getTiposProductos() {
        return tiposProductos;
    }

    public void setTiposProductos(List<TipoProducto> tiposProductos) {
        this.tiposProductos = tiposProductos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.idFamiliaProducto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FamiliaProducto)) {
            return false;
        }
        final FamiliaProducto other = (FamiliaProducto) object;
        return (this.idFamiliaProducto == other.idFamiliaProducto);
    }

    

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.FamiliaProducto[ idFamiliaProducto=" + idFamiliaProducto + " ]";
    }
    
}
