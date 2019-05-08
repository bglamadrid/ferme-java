package cl.duoc.alumnos.ferme.domain.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Benjamin Guillermo
 */
@Entity
@Table(name = "DETALLE_ORDEN_COMPRA")
@NamedQueries({
    @NamedQuery(name = "DetalleOrdenCompra.findAll", query = "SELECT d FROM DetalleOrdenCompra d")})
public class DetalleOrdenCompra implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected DetalleOrdenCompraPK pk;
    
    @JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Producto producto;
    
    @Column(name = "CANTIDAD")
    private int cantidad;
    
    @JoinColumn(name = "ID_ORDEN_COMPRA", referencedColumnName = "ID_ORDEN_COMPRA", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private OrdenCompra ordenCompra;

    public DetalleOrdenCompra() {
        super();
    }

    public DetalleOrdenCompra(int idOrdenCompra, int idDetalleOrdenCompra) {
        super();
        pk = new DetalleOrdenCompraPK();
        pk.setIdOrdenCompra(idOrdenCompra);
        pk.setIdDetalleOrdenCompra(idDetalleOrdenCompra);
    }

    public DetalleOrdenCompraPK getPk() {
        return pk;
    }

    public void setPk(DetalleOrdenCompraPK pk) {
        this.pk = pk;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pk != null ? pk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DetalleOrdenCompra)) {
            return false;
        }
        DetalleOrdenCompra other = (DetalleOrdenCompra) object;
        return this.pk.equals(other.pk);
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.DetalleOrdenCompra[ detalleOrdenCompraPK=" + pk + " ]";
    }
    
}
