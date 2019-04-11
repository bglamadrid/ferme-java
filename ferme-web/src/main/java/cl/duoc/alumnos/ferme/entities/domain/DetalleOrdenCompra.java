package cl.duoc.alumnos.ferme.entities.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
    protected DetalleOrdenCompraPK detalleOrdenCompraPK;
    
    @Column(name = "CANTIDAD")
    private int cantidad;
    
    @JoinColumn(name = "ID_ORDEN_COMPRA", referencedColumnName = "ID_ORDEN_COMPRA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private OrdenCompra ordenCompra;
    
    @JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO")
    @ManyToOne(optional = false)
    private Producto idProducto;

    public DetalleOrdenCompra() {}

    public DetalleOrdenCompraPK getDetalleOrdenCompraPK() {
        return detalleOrdenCompraPK;
    }

    public void setDetalleOrdenCompraPK(DetalleOrdenCompraPK detalleOrdenCompraPK) {
        this.detalleOrdenCompraPK = detalleOrdenCompraPK;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detalleOrdenCompraPK != null ? detalleOrdenCompraPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DetalleOrdenCompra)) {
            return false;
        }
        DetalleOrdenCompra other = (DetalleOrdenCompra) object;
        return this.detalleOrdenCompraPK.equals(other.detalleOrdenCompraPK);
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.DetalleOrdenCompra[ detalleOrdenCompraPK=" + detalleOrdenCompraPK + " ]";
    }
    
}
