package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.dto.DetalleVentaDTO;
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
@Table(name = "DETALLE_VENTA")
@NamedQueries({
    @NamedQuery(name = "DetalleVenta.findAll", query = "SELECT d FROM DetalleVenta d")})
public class DetalleVenta implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected DetalleVentaPK pk;
    
    @JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Producto producto;
    
    @Column(name = "UNIDADES")
    private int unidades;
    
    @Column(name = "MONTO_DETALLE")
    private int monto;
    
    @JoinColumn(name = "ID_VENTA", referencedColumnName = "ID_VENTA", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Venta venta;

    public DetalleVenta() {
        super();
    }

    public DetalleVenta(int idVenta, int idDetalleVenta) {
        super();
        pk = new DetalleVentaPK();
        pk.setIdVenta(idVenta);
        pk.setIdDetalleVenta(idDetalleVenta);
    }

    public DetalleVentaPK getPk() {
        return pk;
    }

    public void setPk(DetalleVentaPK pk) {
        this.pk = pk;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
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

    public DetalleVentaDTO toDTO() {
        DetalleVentaDTO dto = new DetalleVentaDTO();
        dto.setIdVenta(pk.getIdVenta());
        dto.setIdDetalleVenta(pk.getIdDetalleVenta());
        dto.setMontoDetalleVenta(monto);
        dto.setUnidadesProducto(unidades);
        return dto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pk != null ? pk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DetalleVenta)) {
            return false;
        }
        DetalleVenta other = (DetalleVenta) object;
        return this.pk.equals(other.pk);
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.DetalleVenta[ detalleVentaPK=" + pk + " ]";
    }
    
}
