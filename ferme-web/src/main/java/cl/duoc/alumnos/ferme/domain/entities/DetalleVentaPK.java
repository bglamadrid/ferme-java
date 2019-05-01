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
    private int idDetalleVenta;
    
    @Column(name = "ID_VENTA")
    private int idVenta;

    public DetalleVentaPK() {
        super();
    }

    public int getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(int idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
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
        return (this.idDetalleVenta == other.idDetalleVenta);
    }
    
    

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.DetalleVentaPK[ idDetalleVenta=" + idDetalleVenta + ", idVenta=" + idVenta + " ]";
    }
    
}
