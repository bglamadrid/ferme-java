package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.Producto;
import cl.duoc.alumnos.ferme.domain.entities.TipoProducto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public class ProductoDTO {
    private final static Logger LOG = LoggerFactory.getLogger(ProductoDTO.class);
    
    private Integer idProducto;
    private String codigoProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private Long precioProducto;
    private Integer stockActualProducto;
    private Integer stockCriticoProducto;
    private Integer idTipoProducto;
    private String nombreTipoProducto;
    private Integer idFamiliaProducto;
    private String descripcionFamiliaProducto;

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

    public long getPrecioProducto() {
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

    public Integer getIdFamiliaProducto() {
        return idFamiliaProducto;
    }

    public void setIdFamiliaProducto(Integer idFamiliaProducto) {
        this.idFamiliaProducto = idFamiliaProducto;
    }

    public String getDescripcionFamiliaProducto() {
        return descripcionFamiliaProducto;
    }

    public void setDescripcionFamiliaProducto(String descripcionFamiliaProducto) {
        this.descripcionFamiliaProducto = descripcionFamiliaProducto;
    }
    
    public Producto toEntity() {
        Producto entity = new Producto();
        if (idProducto != null && idProducto != 0) {
            entity.setId(idProducto);
        }
        entity.setCodigo(codigoProducto);
        entity.setNombre(nombreProducto);
        entity.setPrecio(precioProducto);
        entity.setStockActual(stockActualProducto);
        entity.setStockCritico(stockCriticoProducto);
        
        if (descripcionProducto != null && !descripcionProducto.isEmpty()) {
            entity.setDescripcion(descripcionProducto);
        }
        
        TipoProducto tipoEntity = new TipoProducto();
        tipoEntity.setId(idTipoProducto);
        entity.setTipo(tipoEntity);
        
        return entity;
    }

    @Override
    public String toString() {
        return "ProductoDTO{" + "idProducto=" + idProducto + ", codigoProducto=" + codigoProducto + ", nombreProducto=" + nombreProducto + ", descripcionProducto=" + descripcionProducto + ", precioProducto=" + precioProducto + ", stockActualProducto=" + stockActualProducto + ", stockCriticoProducto=" + stockCriticoProducto + ", idTipoProducto=" + idTipoProducto + ", nombreTipoProducto=" + nombreTipoProducto + ", idFamiliaProducto=" + idFamiliaProducto + ", nombreFamiliaProducto=" + descripcionFamiliaProducto + '}';
    }

}
