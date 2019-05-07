package cl.duoc.alumnos.ferme.controllers.gestion;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.dto.FamiliaProductoDTO;
import cl.duoc.alumnos.ferme.dto.ProductoDTO;
import cl.duoc.alumnos.ferme.dto.TipoProductoDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IFamiliasProductoService;
import cl.duoc.alumnos.ferme.services.interfaces.IProductosService;
import cl.duoc.alumnos.ferme.services.interfaces.ITiposProductoService;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Benjamin Guillermo
 */
@RestController
@RequestMapping("/api/gestion")
public class ProductosController {

    @Autowired private ITiposProductoService tpProductoSvc;
    @Autowired private IFamiliasProductoService fmlProductoSvc;
    @Autowired private IProductosService productoSvc;
    private final static Logger LOG = LoggerFactory.getLogger(ProductosController.class);

    
    @GetMapping("/familias_producto")
    public Collection<FamiliaProductoDTO> getFamiliasProducto(@RequestParam Map<String,String> allRequestParams) {
        
        Predicate filtros = null;
        if (allRequestParams != null && !allRequestParams.isEmpty()) {
            filtros = this.fmlProductoSvc.queryParamsMapToFamiliasProductosFilteringPredicate(allRequestParams);
        }
        return this.fmlProductoSvc.getFamiliasProductos(filtros);
    }
    
    @GetMapping("/tipos_producto")
    public Collection<TipoProductoDTO> getTiposProducto(@RequestParam Map<String,String> allRequestParams) {
        Predicate filtros = null;
        if (allRequestParams != null && !allRequestParams.isEmpty()) {
            filtros = this.fmlProductoSvc.queryParamsMapToFamiliasProductosFilteringPredicate(allRequestParams);
        }
        return this.tpProductoSvc.getTiposProductos(filtros);
    }
    
    @GetMapping("/productos")
    public Collection<ProductoDTO> getProductos(
            @RequestParam Map<String, String> allRequestParams
    ) {
        return this.getProductos(null, null,  allRequestParams);
    }
    
    @GetMapping("/productos/{pageSize}")
    public Collection<ProductoDTO> getProductos(
            @PathVariable Integer pageSize,
            @RequestParam Map<String, String> allRequestParams
    ) {
        return this.getProductos(pageSize, null, allRequestParams);
    }
    
    @GetMapping("/productos/{pageSize}/{pageIndex}")
    public Collection<ProductoDTO> getProductos(
            @PathVariable Integer pageSize,
            @PathVariable Integer pageIndex,
            @RequestParam Map<String, String> allRequestParams
    ) {
        Integer finalPageSize = Ferme.DEFAULT_PAGE_SIZE;
        Integer finalPageIndex = Ferme.DEFAULT_PAGE_INDEX;
        Predicate condiciones = null;
        
        if (pageSize != null && pageSize > 0) {
            finalPageSize = pageSize;
        }
        if (pageIndex != null && pageIndex > 0) {
            finalPageIndex = pageIndex-1;
        }
        if (allRequestParams != null && !allRequestParams.isEmpty()) {
            condiciones = productoSvc.queryParamsMapToProductosFilteringPredicate(allRequestParams);
        }
        
        return this.productoSvc.getProductos(finalPageSize, finalPageIndex, condiciones);
    }
    
    
    /**
     * Almacena un FamiliaProducto nuevo o actualiza uno existente.
     * @param dto Un objeto DTO representando la FamiliaProducto a almacenar/actualizar.
     * @return El ID de la familia de productos.
     */
    @PostMapping("/familias_producto/guardar")
    public Integer saveFamiliaProducto(@RequestBody FamiliaProductoDTO dto) {
        
        if (dto != null) {
            return fmlProductoSvc.saveFamiliaProducto(dto);
        }
        return null;
    }
    
    /**
     * Almacena un TipoProducto nuevo o actualiza uno existente.
     * @param dto Un objeto DTO representando el TipoProducto a almacenar/actualizar.
     * @return El ID del tipo de producto.
     */
    @PostMapping("/tipos_producto/guardar")
    public Integer saveTipoProducto(@RequestBody TipoProductoDTO dto) {
        
        if (dto != null) {
            return tpProductoSvc.saveTipoProducto(dto);
        }
        return null;
    }
    
    /**
     * Almacena un Producto nuevo o actualiza uno existente.
     * @param dto Un objeto DTO representando el Producto a almacenar/actualizar.
     * @return El ID del producto.
     */
    @PostMapping("/productos/guardar")
    public Integer saveProducto(@RequestBody ProductoDTO dto) {
        
        if (dto != null) {
            return productoSvc.saveProducto(dto);
        }
        return null;
    }
    
    /**
     * Elimina un TipoProducto de la base de datos.
     * @param tipoProductoId El ID del TipoProducto a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue.
     */
    @DeleteMapping("/tipos_producto/borrar")
    public boolean deleteTipoProducto(@RequestParam("id") Integer tipoProductoId) {
        
        if (tipoProductoId != null && tipoProductoId != 0) {
            return tpProductoSvc.deleteTipoProducto(tipoProductoId);
        }
        return false;
    }
    
    /**
     * Elimina un FamiliaProducto de la base de datos.
     * @param familiaProductoId El ID de la FamiliaProducto a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue.
     */
    @DeleteMapping("/familias_producto/borrar")
    public boolean deleteFamiliaProducto(@RequestParam("id") Integer familiaProductoId) {
        
        if (familiaProductoId != null && familiaProductoId != 0) {
            return tpProductoSvc.deleteTipoProducto(familiaProductoId);
        }
        return false;
    }
    
    /**
     * Elimina un Producto de la base de datos.
     * @param productoId El ID de la Producto a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue.
     */
    @DeleteMapping("/productos/borrar")
    public boolean deleteProducto(@RequestParam("id") Integer productoId) {
        
        if (productoId != null && productoId != 0) {
            return productoSvc.deleteProducto(productoId);
        }
        return false;
    }
}
