package cl.duoc.alumnos.ferme.entities.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Benjamin Guillermo
 */
@Embeddable
public class DetalleOrdenCompraPK implements Serializable {

    @Column(name = "ID_DETALLE_ORDEN_COMPRA")
    private int idDetalleOrdenCompra;
    
    @Column(name = "ID_ORDEN_COMPRA")
    private int idOrdenCompra;

    public DetalleOrdenCompraPK() {}

    public void setIdDetalleOrdenCompra(int idDetalleOrdenCompra) {
        this.idDetalleOrdenCompra = idDetalleOrdenCompra;
    }

    public int getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(int idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + this.idDetalleOrdenCompra;
        hash = 83 * hash + this.idOrdenCompra;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DetalleOrdenCompraPK)) {
            return false;
        }
        final DetalleOrdenCompraPK other = (DetalleOrdenCompraPK) object;
        return (this.idDetalleOrdenCompra == other.idDetalleOrdenCompra);
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.DetalleOrdenCompraPK[ idDetalleOrdenCompra=" + idDetalleOrdenCompra + ", idOrdenCompra=" + idOrdenCompra + " ]";
    }
    
}
