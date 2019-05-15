package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.Producto;

/**
 *
 * @author Benjamin Guillermo
 */
public class ProductoDTO {
    
    private Integer idProducto;
    private String codigoProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private Long precioProducto;
    private Integer stockActualProducto;
    private Integer stockCriticoProducto;
    private Integer idTipoProducto;
    private String nombreTipoProducto;

    public ProductoDTO() {
        super();
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Integer getStockActualProducto() {
        return stockActualProducto;
    }

    public void setStockActualProducto(Integer stockActualProducto) {
        this.stockActualProducto = stockActualProducto;
    }

    public Integer getStockCriticoProducto() {
        return stockCriticoProducto;
    }

    public void setStockCriticoProducto(Integer stockCriticoProducto) {
        this.stockCriticoProducto = stockCriticoProducto;
    }

    public long getPrecio() {
        return precioProducto;
    }

    public void setPrecioProducto(Long precioProducto) {
        this.precioProducto = precioProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public Integer getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(Integer idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String getNombreTipoProducto() {
        return nombreTipoProducto;
    }

    public void setNombreTipoProducto(String nombreTipoProducto) {
        this.nombreTipoProducto = nombreTipoProducto;
    }
    
    public Producto toEntity() {
        Producto entity = new Producto();
        entity.setId(idProducto);
        
        entity.setNombre(nombreProducto);
        entity.setDescripcion(descripcionProducto);
        entity.setPrecio(precioProducto);
        entity.setStockActual(stockActualProducto);
        entity.setStockCritico(stockCriticoProducto);
        
        return entity;
    }

}
