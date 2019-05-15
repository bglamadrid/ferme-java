package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.DetalleOrdenCompra;
import cl.duoc.alumnos.ferme.domain.entities.DetalleOrdenCompraPK;
import cl.duoc.alumnos.ferme.domain.entities.Producto;

/**
 *
 * @author Benjamin Guillermo
 */
public class DetalleOrdenCompraDTO {
    
    private Integer idDetalleOrdenCompra;
    private Integer idOrdenCompra;
    private Integer idProducto;
    private Integer cantidadProducto;

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
    
    public DetalleOrdenCompra toEntity() {
        DetalleOrdenCompra entity = new DetalleOrdenCompra();
        DetalleOrdenCompraPK entityPK = new DetalleOrdenCompraPK();
        if (idOrdenCompra != 0) { entityPK.setIdOrdenCompra(idOrdenCompra); }
        if (idDetalleOrdenCompra != 0) { entityPK.setIdDetalleOrdenCompra(idDetalleOrdenCompra); }
        entity.setPk(entityPK);
        
        entity.setCantidad(cantidadProducto);
        
        entity.setProducto(new Producto(idProducto));
        
        return entity;
    }
    
}
