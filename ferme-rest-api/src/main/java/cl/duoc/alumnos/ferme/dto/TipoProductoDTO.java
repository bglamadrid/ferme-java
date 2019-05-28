package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.FamiliaProducto;
import cl.duoc.alumnos.ferme.domain.entities.TipoProducto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Benjamin Guillermo
 */
public class TipoProductoDTO {
    private final static Logger LOG = LoggerFactory.getLogger(TipoProductoDTO.class);
    
    private Integer idTipoProducto;
    private String nombreTipoProducto;
    private Integer idFamiliaProducto;
    private String nombreFamiliaProducto;

    public TipoProductoDTO() {
        super();
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

    public String getNombreFamiliaProducto() {
        return nombreFamiliaProducto;
    }

    public void setNombreFamiliaProducto(String nombreFamiliaProducto) {
        this.nombreFamiliaProducto = nombreFamiliaProducto;
    }
    
    public TipoProducto toEntity() {
        TipoProducto entity = new TipoProducto();
        try {
            if (idTipoProducto != 0) {
                entity.setId(idTipoProducto);
            }
        } catch (NullPointerException exc) {
            LOG.info("toEntity() - idTipoProducto es null");
        }
        
        entity.setNombre(nombreTipoProducto);
        
        return entity;
    }

    @Override
    public String toString() {
        return "TipoProductoDTO{" + "idTipoProducto=" + idTipoProducto + ", nombreTipoProducto=" + nombreTipoProducto + ", idFamiliaProducto=" + idFamiliaProducto + ", nombreFamiliaProducto=" + nombreFamiliaProducto + '}';
    }
    
}
