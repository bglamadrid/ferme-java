package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.dto.DetalleVentaDTO;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
 * @author Benjamin Guillermo
 */
@Entity
@Table(name = "DETALLE_VENTA")
@NamedQueries({
    @NamedQuery(name = "DetalleVenta.findAll", query = "SELECT d FROM DetalleVenta d")})
public class DetalleVenta implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_DETALLE_VENTA")
    @SequenceGenerator(name = "detalle_venta_seq", sequenceName = "SEQ_DETALLE_VENTA", initialValue = 1, allocationSize = Ferme.DEFAULT_HIBERNATE_SEQUENCES_ALLOCATION_SIZE)
    @GeneratedValue(generator = "detalle_venta_seq", strategy = GenerationType.AUTO)
    private Integer _id;
    
    @JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO")
    @ManyToOne(cascade = CascadeType.DETACH, optional = false, fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    private Producto _producto;
    
    @Column(name = "UNIDADES")
    private int _unidades;
    
    @Column(name = "MONTO_DETALLE")
    private int _monto;
    
    @JoinColumn(name = "ID_VENTA", referencedColumnName = "ID_VENTA")
    @ManyToOne(cascade = CascadeType.DETACH, optional = false, fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    private Venta _venta;

    public DetalleVenta() {
        super();
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        this._id = id;
    }

    public int getUnidades() {
        return _unidades;
    }

    public void setUnidades(int unidades) {
        this._unidades = unidades;
    }

    public int getMonto() {
        return _monto;
    }

    public void setMonto(int monto) {
        this._monto = monto;
    }

    public Producto getProducto() {
        return _producto;
    }

    public void setProducto(Producto producto) {
        this._producto = producto;
    }

    public Venta getVenta() {
        return _venta;
    }

    public void setVenta(Venta venta) {
        this._venta = venta;
    }

    public DetalleVentaDTO toDTO() {
        DetalleVentaDTO dto = new DetalleVentaDTO();
        dto.setIdVenta(_venta.getId());
        dto.setIdDetalleVenta(_id);
        dto.setMontoDetalleVenta(_monto);
        dto.setUnidadesProducto(_unidades);
        
        Producto productoEntity = getProducto();
        dto.setIdProducto(productoEntity.getId());
        dto.setCodigoProducto(productoEntity.getCodigo());
        dto.setNombreProducto(productoEntity.getNombre());
        dto.setPrecioProducto(productoEntity.getPrecio());
        
        return dto;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this._id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DetalleVenta)) {
            return false;
        }
        DetalleVenta other = (DetalleVenta) obj;
        return (Objects.equals(this._id, other.getId()));
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.DetalleVenta[ id=" + _id + " ]";
    }
    
}
