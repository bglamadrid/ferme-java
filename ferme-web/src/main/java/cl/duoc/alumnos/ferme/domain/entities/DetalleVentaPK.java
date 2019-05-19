package cl.duoc.alumnos.ferme.domain.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Benjamin Guillermo
 */
@Embeddable
public class DetalleVentaPK implements Serializable {

    @Column(name = "ID_DETALLE_VENTA")
    private Integer idDetalleVenta;
    
    @Column(name = "ID_VENTA")
    private Integer idVenta;

    public DetalleVentaPK() {
        super();
    }

    public Integer getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(Integer idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.idDetalleVenta;
        hash = 97 * hash + this.idVenta;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DetalleVentaPK)) {
            return false;
        }
        final DetalleVentaPK other = (DetalleVentaPK) object;
        return (this.getIdDetalleVenta() == other.getIdDetalleVenta());
    }
    
    

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.DetalleVentaPK[ idDetalleVenta=" + idDetalleVenta + ", idVenta=" + idVenta + " ]";
    }
    
}
