package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.DetalleVenta;
import cl.duoc.alumnos.ferme.domain.entities.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Benjamin Guillermo
 */
public class DetalleVentaDTO {
    private final static Logger LOG = LoggerFactory.getLogger(DetalleVentaDTO.class);
    
    private Integer idDetalleVenta;
    private Integer idVenta;
    private Integer montoDetalleVenta;
    private Integer idProducto;
    private Integer unidadesProducto;
    private String nombreProducto;
    private Long precioProducto;
    private Long codigoProducto;

    public DetalleVentaDTO() {
        super();
    }

    public Integer getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(Integer idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public Integer getMontoDetalleVenta() {
        return montoDetalleVenta;
    }

    public void setMontoDetalleVenta(Integer montoDetalleVenta) {
        this.montoDetalleVenta = montoDetalleVenta;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getUnidadesProducto() {
        return unidadesProducto;
    }

    public void setUnidadesProducto(Integer unidadesProducto) {
        this.unidadesProducto = unidadesProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Long getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(Long precioProducto) {
        this.precioProducto = precioProducto;
    }

    public Long getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(Long codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public DetalleVenta toEntity() {
        DetalleVenta entity = new DetalleVenta();
        try {
            if (idDetalleVenta != 0) {
                entity.setId(idDetalleVenta);
            }
        } catch (NullPointerException exc) {
            LOG.info("toEntity() - idDetalleVenta es null");
        }
        
        entity.setMonto(montoDetalleVenta);
        entity.setUnidades(unidadesProducto);
        
        entity.setProducto(new Producto(idProducto));
        
        return entity;
    }

    @Override
    public String toString() {
        return "DetalleVentaDTO{" + "idDetalleVenta=" + idDetalleVenta + ", idVenta=" + idVenta + ", idProducto=" + idProducto + ", unidadesProducto=" + unidadesProducto + ", montoDetalleVenta=" + montoDetalleVenta + ", nombreProducto=" + nombreProducto + ", codigoProducto=" + codigoProducto + '}';
    }
    
}
