package cl.duoc.alumnos.ferme.dto;

/**
 *
 * @author Benjamin Guillermo
 */
public class TipoProductoDTO {
    
    private Integer idTipoProducto;
    private String nombreTipo;
    private Integer idFamiliaProducto;

    public TipoProductoDTO() {}

    public Integer getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(Integer idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public Integer getIdFamiliaProducto() {
        return idFamiliaProducto;
    }

    public void setIdFamiliaProducto(Integer idFamiliaProducto) {
        this.idFamiliaProducto = idFamiliaProducto;
    }
    
}
