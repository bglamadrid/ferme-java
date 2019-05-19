package cl.duoc.alumnos.ferme.domain.entities;

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
    private Integer idDetalleOrdenCompra;
    
    @Column(name = "ID_ORDEN_COMPRA")
    private Integer idOrdenCompra;

    public DetalleOrdenCompraPK() {
        super();
    }

    public Integer getIdDetalleOrdenCompra() {
        return idDetalleOrdenCompra;
    }

    public void setIdDetalleOrdenCompra(Integer idDetalleOrdenCompra) {
        this.idDetalleOrdenCompra = idDetalleOrdenCompra;
    }

    public Integer getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(Integer idOrdenCompra) {
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
        return (this.getIdDetalleOrdenCompra() == other.getIdDetalleOrdenCompra());
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.DetalleOrdenCompraPK[ idDetalleOrdenCompra=" + idDetalleOrdenCompra + ", idOrdenCompra=" + idOrdenCompra + " ]";
    }
    
}
