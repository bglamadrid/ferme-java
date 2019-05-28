package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.Proveedor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Benjamin Guillermo
 */
public class ProveedorDTO extends PersonaDTO {
    private final static Logger LOG = LoggerFactory.getLogger(ProveedorDTO.class);
    
    private Integer idProveedor;
    private String razonSocialProveedor;
    private Integer idRubro;

    public ProveedorDTO() {
        super();
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getRazonSocialProveedor() {
        return razonSocialProveedor;
    }

    public void setRazonSocialProveedor(String razonSocialProveedor) {
        this.razonSocialProveedor = razonSocialProveedor;
    }

    public Integer getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(Integer idRubro) {
        this.idRubro = idRubro;
    }
    
    public Proveedor toEntity() {
        Proveedor entity = new Proveedor();
        try {
            if (idProveedor != 0) {
                entity.setId(idProveedor);
            }
        } catch (NullPointerException exc) {
            LOG.info("toEntity() - idProveedor es null");
        }
        
        entity.setRazonSocial(razonSocialProveedor);
        
        entity.setPersona(this.personaToEntity());
        
        return entity;
    }

    @Override
    public String toString() {
        return "ProveedorDTO{" + "idProveedor=" + idProveedor + ", razonSocialProveedor=" + razonSocialProveedor + ", idRubro=" + idRubro+ ", " + super.toString() + '}';
    }
    
}
