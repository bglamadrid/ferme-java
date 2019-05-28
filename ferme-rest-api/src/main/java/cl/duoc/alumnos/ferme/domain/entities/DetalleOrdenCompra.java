package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.dto.DetalleOrdenCompraDTO;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
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
    
    @Id
    @Column(name = "ID_DETALLE_ORDEN_COMPRA")
    @SequenceGenerator(name = "detalle_orden_compra_seq", sequenceName = "SEQ_DETALLE_ORDEN_COMPRA", initialValue = 1, allocationSize = Ferme.DEFAULT_HIBERNATE_SEQUENCES_ALLOCATION_SIZE)
    @GeneratedValue(generator = "detalle_orden_compra_seq", strategy = GenerationType.AUTO)
    protected Integer id;
    
    @JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO", insertable = false, updatable = false)
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    
    public DetalleOrdenCompraDTO toDTO() {
        DetalleOrdenCompraDTO dto = new DetalleOrdenCompraDTO();
        dto.setIdDetalleOrdenCompra(id);
        dto.setCantidadProducto(cantidad);
        dto.setIdProducto(producto.getId());
        dto.setIdOrdenCompra(ordenCompra.getId());
        return dto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DetalleOrdenCompra)) {
            return false;
        }
        final DetalleOrdenCompra other = (DetalleOrdenCompra) obj;
        return (Objects.equals(this.id, other.getId()));
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.DetalleOrdenCompra[ id=" + id + " ]";
    }
    
}
