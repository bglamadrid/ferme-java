package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.dto.DetalleVentaDTO;
import java.io.Serializable;
import java.util.Objects;
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
    private Integer id;
    
    @JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Producto producto;
    
    @Column(name = "UNIDADES")
    private int unidades;
    
    @Column(name = "MONTO_DETALLE")
    private int monto;
    
    @JoinColumn(name = "ID_VENTA", referencedColumnName = "ID_VENTA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Venta venta;

    public DetalleVenta() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        dto.setIdDetalleVenta(id);
        dto.setIdVenta(venta.getId());
        dto.setMontoDetalleVenta(monto);
        
        Producto productoEntity = this.getProducto();
        dto.setIdProducto(productoEntity.getId());
        dto.setNombreProducto(productoEntity.getNombre());
        dto.setPrecioProducto(productoEntity.getPrecio());
        dto.setUnidadesProducto(unidades);
        
        return dto;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DetalleVenta)) {
            return false;
        }
        DetalleVenta other = (DetalleVenta) obj;
        return (Objects.equals(this.id, other.getId()));
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.DetalleVenta[ id=" + id + " ]";
    }
    
}
