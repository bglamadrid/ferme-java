package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.DetalleOrdenCompraDTO;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
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
import org.hibernate.annotations.Cascade;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Entity
@Table(name = "DETALLE_ORDEN_COMPRA")
@NamedQueries({
    @NamedQuery(name = "DetalleOrdenCompra.findAll", query = "SELECT d FROM DetalleOrdenCompra d")})
public class DetalleOrdenCompra implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_DETALLE_ORDEN_COMPRA")
    @SequenceGenerator(name = "detalle_orden_compra_seq", sequenceName = "SEQ_DETALLE_ORDEN_COMPRA", initialValue = 1, allocationSize = FermeConfig.DEFAULT_HIBERNATE_SEQUENCES_ALLOCATION_SIZE)
    @GeneratedValue(generator = "detalle_orden_compra_seq", strategy = GenerationType.AUTO)
    protected Integer _id;
    
    @JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO")
    @ManyToOne(cascade = CascadeType.DETACH, optional = false, fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    private Producto _producto;
    
    @Column(name = "CANTIDAD")
    private int _cantidad;
    
    @JoinColumn(name = "ID_ORDEN_COMPRA", referencedColumnName = "ID_ORDEN_COMPRA")
    @ManyToOne(cascade = CascadeType.DETACH, optional = false, fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    private OrdenCompra _ordenCompra;

    public DetalleOrdenCompra() {
        super();
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        this._id = id;
    }

    public int getCantidad() {
        return _cantidad;
    }

    public void setCantidad(int cantidad) {
        this._cantidad = cantidad;
    }

    public Producto getProducto() {
        return _producto;
    }

    public void setProducto(Producto producto) {
        this._producto = producto;
    }

    public OrdenCompra getOrdenCompra() {
        return _ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this._ordenCompra = ordenCompra;
    }
    
    public DetalleOrdenCompraDTO toDTO() {
        DetalleOrdenCompraDTO dto = new DetalleOrdenCompraDTO();
        dto.setIdOrdenCompra(_ordenCompra.getId());
        dto.setIdDetalleOrdenCompra(_id);
        dto.setCantidadProducto(_cantidad);
        
        Producto productoEntity = this.getProducto();
        dto.setIdProducto(productoEntity.getId());
        dto.setCodigoProducto(productoEntity.getCodigo());
        dto.setNombreProducto(productoEntity.getNombre());
        dto.setPrecioProducto(productoEntity.getPrecio());
        
        return dto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this._id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DetalleOrdenCompra)) {
            return false;
        }
        final DetalleOrdenCompra other = (DetalleOrdenCompra) obj;
        return (Objects.equals(this._id, other.getId()));
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.DetalleOrdenCompra[ id=" + _id + " ]";
    }
    
}
