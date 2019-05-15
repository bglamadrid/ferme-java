package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.FamiliaProducto;
import cl.duoc.alumnos.ferme.domain.entities.Proveedor;
import cl.duoc.alumnos.ferme.domain.entities.Rubro;

/**
 *
 * @author Benjamin Guillermo
 */
public class FamiliaProductoDTO {
    
    private Integer idFamiliaProducto;
    private String descripcionFamiliaProducto;
    private Integer idRubro;
    private String descripcionRubro;
    private Integer idProveedor;

    public FamiliaProductoDTO() {
        super();
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

    public Integer getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(Integer idRubro) {
        this.idRubro = idRubro;
    }

    public String getDescripcionRubro() {
        return descripcionRubro;
    }

    public void setDescripcionRubro(String descripcionRubro) {
        this.descripcionRubro = descripcionRubro;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }
    
    public FamiliaProducto toEntity() {
        FamiliaProducto entity = new FamiliaProducto();
        if (idFamiliaProducto != 0) {
            entity.setId(idFamiliaProducto);
        }
        
        entity.setDescripcion(descripcionFamiliaProducto);
        
        entity.setProveedor(new Proveedor(idProveedor));
        entity.setRubro(new Rubro(idRubro));
        
        return entity;
    }
    
}
