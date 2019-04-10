package cl.duoc.alumnos.ferme.entities.domain;

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
import javax.persistence.Table;
import javax.validation.constraints.Size;

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
    private int idTipoProducto;
    
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE_TIPO")
    private String nombreTipo;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoProducto")
    private List<Producto> productos;
    
    @JoinColumn(name = "ID_FAMILIA_PRODUCTO", referencedColumnName = "ID_FAMILIA_PRODUCTO")
    @ManyToOne(optional = false)
    private FamiliaProducto familiaProducto;

    public TipoProducto() {}

    public int getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public FamiliaProducto getFamiliaProducto() {
        return familiaProducto;
    }

    public void setFamiliaProducto(FamiliaProducto familiaProducto) {
        this.familiaProducto = familiaProducto;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.idTipoProducto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TipoProducto)) {
            return false;
        }
        final TipoProducto other = (TipoProducto) object;
        return (this.idTipoProducto == other.idTipoProducto);
    }
    
    

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.TipoProducto[ idTipoProducto=" + idTipoProducto + " ]";
    }
    
}
