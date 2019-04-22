package cl.duoc.alumnos.ferme.dto;

/**
 *
 * @author Benjamin Guillermo
 */
public class TipoProductoDTO {
    
    private Integer idTipoProducto;
    private String nombreTipoProducto;
    private Integer idFamiliaProducto;
    private String nombreFamiliaProducto;

    public TipoProductoDTO() {}

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
    
}
