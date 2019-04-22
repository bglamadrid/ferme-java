package cl.duoc.alumnos.ferme.dto;

/**
 *
 * @author Benjamin Guillermo
 */
public class FamiliaProductoDTO {
    
    private Integer idFamiliaProducto;
    private String descripcionFamiliaProducto;
    private Integer idRubro;
    private String descripcionRubro;

    public FamiliaProductoDTO() {
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
    
}
