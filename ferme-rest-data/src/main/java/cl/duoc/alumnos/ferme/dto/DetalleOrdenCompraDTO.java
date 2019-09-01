package cl.duoc.alumnos.ferme.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.duoc.alumnos.ferme.entities.DetalleOrdenCompra;
import cl.duoc.alumnos.ferme.entities.Producto;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public class DetalleOrdenCompraDTO {
    @SuppressWarnings("unused")
	private final static Logger LOG = LoggerFactory.getLogger(DetalleOrdenCompraDTO.class);
    
    private Integer idDetalleOrdenCompra;
    private Integer idOrdenCompra;
    private Integer idProducto;
    private Integer cantidadProducto;
    private String nombreProducto;
    private long precioProducto;
    private String codigoProducto;

    public DetalleOrdenCompraDTO() {
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

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(Integer cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public long getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(long precioProducto) {
        this.precioProducto = precioProducto;
    }
    
    public DetalleOrdenCompra toEntity() {
        DetalleOrdenCompra entity = new DetalleOrdenCompra();
        
        if (idDetalleOrdenCompra != null && idDetalleOrdenCompra != 0) { 
            entity.setId(idDetalleOrdenCompra); 
        }
        entity.setCantidad(cantidadProducto);
        
        Producto productoEntity = new Producto();
        productoEntity.setId(idProducto);
        entity.setProducto(productoEntity);
        
        
        return entity;
    }

    @Override
    public String toString() {
        return "DetalleOrdenCompraDTO{" + "idDetalleOrdenCompra=" + idDetalleOrdenCompra + ", idOrdenCompra=" + idOrdenCompra + ", idProducto=" + idProducto + ", cantidadProducto=" + cantidadProducto + '}';
    }
    
}
