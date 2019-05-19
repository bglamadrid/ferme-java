package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.dto.FamiliaProductoDTO;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
    private int id;
    
    @Size(min = 1, max = 100)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @JoinColumn(name = "ID_RUBRO", referencedColumnName = "ID_RUBRO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Rubro rubro;
    
    @JoinColumn(name = "ID_PROVEEDOR", referencedColumnName = "ID_PROVEEDOR", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Proveedor proveedor;

    public FamiliaProducto() {
        super();
    }

    public FamiliaProducto(int id) {
        super();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public FamiliaProductoDTO toDTO() {
        FamiliaProductoDTO dto = new FamiliaProductoDTO();
        
        dto.setIdFamiliaProducto(id);
        dto.setIdRubro(rubro.getId());
        dto.setIdProveedor(proveedor.getId());
        dto.setDescripcionFamiliaProducto(descripcion);
        dto.setDescripcionRubro(rubro.getDescripcion());
        
        return dto;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FamiliaProducto)) {
            return false;
        }
        final FamiliaProducto other = (FamiliaProducto) object;
        return (this.id == other.getId());
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.FamiliaProducto[ idFamiliaProducto=" + id + " ]";
    }
    
}
