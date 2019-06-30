package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.ProductoDTO;
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
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.NonNull;

/** Representa un registro de la tabla PRODUCTO.
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Entity
@Table(name = "PRODUCTO")
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p")})
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_PRODUCTO")
    @SequenceGenerator(name = "producto_seq", sequenceName = "SEQ_PRODUCTO", initialValue = 1, allocationSize = FermeConfig.ESPACIO_ASIGNACION_SECUENCIAS_HIBERNATE)
    @GeneratedValue(generator = "producto_seq", strategy = GenerationType.AUTO)
    private Integer _id;
    
    @Column(name = "CODIGO")
    private String _codigo;
    
    @NonNull
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE")
    private String _nombre;
    
    @NonNull
    @Column(name = "STOCK_ACTUAL")
    private int _stockActual;
    
    @NonNull
    @Column(name = "STOCK_CRITICO")
    private int _stockCritico;
    
    @NonNull
    @Column(name = "PRECIO")
    private long _precio;
    
    @Size(max = 300)
    @Column(name = "DESCRIPCION")
    private String _descripcion;
    
    @NonNull
    @JoinColumn(name = "ID_TIPO_PRODUCTO", referencedColumnName = "ID_TIPO_PRODUCTO")
    @ManyToOne(cascade = CascadeType.DETACH, optional = false, fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    private TipoProducto _tipo;

    public Producto() {
        super();
    }

    public Integer getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getCodigo() {
        return _codigo;
    }

    public void setCodigo(String codigo) {
        this._codigo = codigo;
    }

    public String getNombre() {
        return _nombre;
    }

    public void setNombre(String nombre) {
        this._nombre = nombre;
    }

    public int getStockActual() {
        return _stockActual;
    }

    public void setStockActual(int stockActual) {
        this._stockActual = stockActual;
    }

    public int getStockCritico() {
        return _stockCritico;
    }

    public void setStockCritico(int stockCritico) {
        this._stockCritico = stockCritico;
    }

    public long getPrecio() {
        return _precio;
    }

    public void setPrecio(long precio) {
        this._precio = precio;
    }

    public String getDescripcion() {
        return _descripcion;
    }

    public void setDescripcion(String descripcion) {
        this._descripcion = descripcion;
    }

    public TipoProducto getTipo() {
        return _tipo;
    }

    public void setTipo(TipoProducto tipo) {
        this._tipo = tipo;
    }

    public ProductoDTO toDTO() {
        ProductoDTO dto = new ProductoDTO();
        TipoProducto tipoEntity = this.getTipo();
        FamiliaProducto familiaEntity = tipoEntity.getFamilia();
        
        dto.setIdProducto(_id);
        dto.setCodigoProducto(_codigo);
        dto.setNombreProducto(_nombre);
        dto.setDescripcionProducto(_descripcion);
        dto.setPrecioProducto(_precio);
        dto.setStockActualProducto(_stockActual);
        dto.setStockCriticoProducto(_stockCritico);
        
        dto.setIdTipoProducto(tipoEntity.getId());
        dto.setNombreTipoProducto(tipoEntity.getNombre());
        
        dto.setIdFamiliaProducto(familiaEntity.getId());
        dto.setDescripcionFamiliaProducto(familiaEntity.getDescripcion());
        
        return dto;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this._id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Producto)) {
            return false;
        }
        final Producto other = (Producto) object;
        return (Objects.equals(this._id, other.getId()));
    }
    
    

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Producto[ id=" + _id + " ]";
    }
    
}
