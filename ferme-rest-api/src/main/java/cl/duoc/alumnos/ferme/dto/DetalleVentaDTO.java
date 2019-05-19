package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.DetalleVenta;
import cl.duoc.alumnos.ferme.domain.entities.DetalleVentaPK;
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
    private Integer idProducto;
    private Integer unidadesProducto;
    private Integer montoDetalleVenta;
    private Integer idVenta;

    public DetalleVentaDTO() {
        super();
    }

    public Integer getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(Integer idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
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

    public Integer getMontoDetalleVenta() {
        return montoDetalleVenta;
    }

    public void setMontoDetalleVenta(Integer montoDetalleVenta) {
        this.montoDetalleVenta = montoDetalleVenta;
    }

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public DetalleVenta toEntity() {
        DetalleVenta entity = new DetalleVenta();
        DetalleVentaPK entityPK = new DetalleVentaPK();
        try {
            if (idVenta != 0) {
                entityPK.setIdVenta(idVenta);
            }
        } catch (NullPointerException exc) {
            LOG.info("toEntity() - idVenta es null");
        }
        try {
            if (idDetalleVenta != 0) {
                entityPK.setIdDetalleVenta(idDetalleVenta);
            }
        } catch (NullPointerException exc) {
            LOG.info("toEntity() - idDetalleVenta es null");
        }
        
        entity.setMonto(montoDetalleVenta);
        entity.setUnidades(unidadesProducto);
        
        entity.setProducto(new Producto(idProducto));
        
        return entity;
    }
    
}
