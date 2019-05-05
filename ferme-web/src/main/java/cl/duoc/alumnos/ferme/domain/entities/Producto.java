package cl.duoc.alumnos.ferme.domain.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Benjamin Guillermo
 */
@Entity
@Table(name = "PRODUCTO")
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p")})
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_PRODUCTO")
    private int id;
    
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "STOCK_ACTUAL")
    private int stockActual;
    
    @Column(name = "STOCK_CRITICO")
    private int stockCritico;
    
    @Column(name = "PRECIO")
    private long precio;
    
    @Size(max = 300)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @JoinColumn(name = "ID_TIPO_PRODUCTO", referencedColumnName = "ID_TIPO_PRODUCTO")
    @ManyToOne(optional = false)
    private TipoProducto tipo;

    public Producto() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    public int getStockCritico() {
        return stockCritico;
    }

    public void setStockCritico(int stockCritico) {
        this.stockCritico = stockCritico;
    }

    public long getPrecio() {
        return precio;
    }

    public void setPrecio(long precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoProducto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Producto)) {
            return false;
        }
        final Producto other = (Producto) object;
        return (this.id == other.id);
    }
    
    

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Producto[ idProducto=" + id + " ]";
    }
    
}
