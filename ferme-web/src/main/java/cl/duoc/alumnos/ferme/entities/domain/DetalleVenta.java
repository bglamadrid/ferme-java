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
@Table(name = "DETALLE_VENTA")
@NamedQueries({
    @NamedQuery(name = "DetalleVenta.findAll", query = "SELECT d FROM DetalleVenta d")})
public class DetalleVenta implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected DetalleVentaPK detalleVentaPK;
    
    @Column(name = "UNIDADES")
    private int unidades;
    
    @Column(name = "MONTO_DETALLE")
    private int montoDetalle;
    
    @JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO")
    @ManyToOne(optional = false)
    private Producto producto;
    
    @JoinColumn(name = "ID_VENTA", referencedColumnName = "ID_VENTA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Venta venta;

    public DetalleVenta() {}

    public DetalleVentaPK getDetalleVentaPK() {
        return detalleVentaPK;
    }

    public void setDetalleVentaPK(DetalleVentaPK detalleVentaPK) {
        this.detalleVentaPK = detalleVentaPK;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public int getMontoDetalle() {
        return montoDetalle;
    }

    public void setMontoDetalle(int montoDetalle) {
        this.montoDetalle = montoDetalle;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detalleVentaPK != null ? detalleVentaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DetalleVenta)) {
            return false;
        }
        DetalleVenta other = (DetalleVenta) object;
        return this.detalleVentaPK.equals(other.detalleVentaPK);
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.DetalleVenta[ detalleVentaPK=" + detalleVentaPK + " ]";
    }
    
}
