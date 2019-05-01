package cl.duoc.alumnos.ferme.domain.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "TIPO_PRODUCTO")
@NamedQueries({
    @NamedQuery(name = "TipoProducto.findAll", query = "SELECT t FROM TipoProducto t")})
public class TipoProducto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_TIPO_PRODUCTO")
    private int id;
    
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE_TIPO")
    private String nombre;
    
    @JoinColumn(name = "ID_FAMILIA_PRODUCTO", referencedColumnName = "ID_FAMILIA_PRODUCTO")
    @ManyToOne(optional = false)
    private FamiliaProducto familia;

    public TipoProducto() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public FamiliaProducto getFamilia() {
        return familia;
    }

    public void setFamilia(FamiliaProducto familia) {
        this.familia = familia;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TipoProducto)) {
            return false;
        }
        final TipoProducto other = (TipoProducto) object;
        return (this.id == other.id);
    }
    
    

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.TipoProducto[ idTipoProducto=" + id + " ]";
    }
    
}
