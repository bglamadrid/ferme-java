package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.Proveedor;

/**
 *
 * @author Benjamin Guillermo
 */
public class ProveedorDTO extends PersonaDTO {
    
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
        if (idProveedor != null) {
            entity.setId(idProveedor);
        }
        
        entity.setRazonSocial(razonSocialProveedor);
        
        entity.setPersona(this.personaToEntity());
        
        return entity;
    }
    
}
