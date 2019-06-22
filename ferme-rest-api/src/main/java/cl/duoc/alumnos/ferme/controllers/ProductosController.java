package cl.duoc.alumnos.ferme.controllers;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.FamiliaProductoDTO;
import cl.duoc.alumnos.ferme.dto.ProductoDTO;
import cl.duoc.alumnos.ferme.dto.TipoProductoDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IFamiliasProductoService;
import cl.duoc.alumnos.ferme.services.interfaces.IProductosService;
import cl.duoc.alumnos.ferme.services.interfaces.ITiposProductoService;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;
import javassist.NotFoundException;
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
    private final static Logger LOG = LoggerFactory.getLogger(ProductosController.class);

    @Autowired private ITiposProductoService tpProductoSvc;
    @Autowired private IFamiliasProductoService fmlProductoSvc;
    @Autowired private IProductosService productoSvc;
    
    @GetMapping("/productos")
    public Collection<ProductoDTO> obtener(
            @RequestParam Map<String, String> allRequestParams
    ) {
        return this.obtener(null, null,  allRequestParams);
    }
    
    @GetMapping("/productos/{pageSize}")
    public Collection<ProductoDTO> obtener(
            @PathVariable Integer pageSize,
            @RequestParam Map<String, String> allRequestParams
    ) {
        return this.obtener(pageSize, null, allRequestParams);
    }
    
    @GetMapping("/productos/{pageSize}/{pageIndex}")
    public Collection<ProductoDTO> obtener(
            @PathVariable Integer pageSize,
            @PathVariable Integer pageIndex,
            @RequestParam Map<String, String> allRequestParams
    ) {
        Integer finalPageSize = FermeConfig.DEFAULT_PAGE_SIZE;
        Integer finalPageIndex = FermeConfig.DEFAULT_PAGE_INDEX;
        Predicate filtros = null;
        
        if (pageSize != null && pageSize > 0) {
            finalPageSize = pageSize;
        }
        if (pageIndex != null && pageIndex > 0) {
            finalPageIndex = pageIndex-1;
        }
        if (allRequestParams != null && !allRequestParams.isEmpty()) {
            filtros = productoSvc.queryParamsMapToProductosFilteringPredicate(allRequestParams);
        }
        
        LOG.info("getProductos - "+finalPageSize+" registros; página "+finalPageIndex);
        LOG.debug("getProductos - Filtros solicitados: "+filtros);
        Collection<ProductoDTO> productos = this.productoSvc.getProductos(finalPageSize, finalPageIndex, filtros);
        LOG.debug("getProductos - productos.size()="+productos.size());
        LOG.info("getProductos - Solicitud completa. Enviando respuesta al cliente.");
        return productos;
    }
    
    /**
     * Almacena un Producto nuevo o actualiza uno existente.
     * @param dto Un objeto DTO representando el Producto a almacenar/actualizar.
     * @return El ID del producto.
     */
    @PostMapping("/productos/guardar")
    public Integer guardar(@RequestBody ProductoDTO dto) throws NotFoundException {
        
        if (dto != null) {
            LOG.debug("saveProducto - dto="+dto);
            Integer productoId =  productoSvc.saveProducto(dto);
            LOG.debug("saveFamiliaProducto - productoId="+productoId);
            return productoId;
        }
        return null;
    }
    
    /**
     * Elimina un Producto de la base de datos.
     * @param productoId El ID del Producto a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue.
     */
    @PostMapping("/productos/borrar")
    public boolean borrar(@RequestBody Integer productoId) {
        
        if (productoId != null && productoId != 0) {
            LOG.debug("deleteProducto - productoId="+productoId);
            return productoSvc.deleteProducto(productoId);
        }
        return false;
    }
    
    @GetMapping("/tipos_producto")
    public Collection<TipoProductoDTO> tipos(@RequestParam Map<String,String> allRequestParams) {
        Predicate filtros = null;
        if (allRequestParams != null && !allRequestParams.isEmpty()) {
            filtros = this.tpProductoSvc.queryParamsMapToTiposProductosFilteringPredicate(allRequestParams);
        }
        return this.tpProductoSvc.getTiposProductos(filtros);
    }
    
    /**
     * Almacena un TipoProducto nuevo o actualiza uno existente.
     * @param dto Un objeto DTO representando el TipoProducto a almacenar/actualizar.
     * @return El ID del tipo de producto.
     */
    @PostMapping("/tipos_producto/guardar")
    public Integer guardarTipo(@RequestBody TipoProductoDTO dto) throws NotFoundException {
        
        if (dto != null) {
            LOG.debug("saveTipoProducto - dto="+dto);
            Integer tipoProductoId = tpProductoSvc.saveTipoProducto(dto);
            LOG.debug("saveFamiliaProducto - tipoProductoId="+tipoProductoId);
            return tipoProductoId;
        }
        return null;
    }
    
    /**
     * Elimina un TipoProducto de la base de datos.
     * @param tipoProductoId El ID del TipoProducto a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue.
     */
    @DeleteMapping("/tipos_producto/borrar")
    public boolean borrarTipo(@RequestParam("id") Integer tipoProductoId) {
        
        if (tipoProductoId != null && tipoProductoId != 0) {
            LOG.debug("deleteTipoProducto - clienteId="+tipoProductoId);
            return tpProductoSvc.deleteTipoProducto(tipoProductoId);
        }
        return false;
    }
    
    @GetMapping("/familias_producto")
    public Collection<FamiliaProductoDTO> familias(@RequestParam Map<String,String> allRequestParams) {
        
        Predicate filtros = null;
        if (allRequestParams != null && !allRequestParams.isEmpty()) {
            filtros = this.fmlProductoSvc.queryParamsMapToFamiliasProductosFilteringPredicate(allRequestParams);
        }
        return this.fmlProductoSvc.getFamiliasProductos(filtros);
    }
    
    
    /**
     * Almacena un FamiliaProducto nuevo o actualiza uno existente.
     * @param dto Un objeto DTO representando la FamiliaProducto a almacenar/actualizar.
     * @return El ID de la familia de productos.
     */
    @PostMapping("/familias_producto/guardar")
    public Integer guardarFamilia(@RequestBody FamiliaProductoDTO dto) throws NotFoundException {
        
        if (dto != null) {
            LOG.debug("saveFamiliaProducto - dto="+dto);
            Integer familiaProductoId = fmlProductoSvc.saveFamiliaProducto(dto);
            LOG.debug("saveFamiliaProducto - familiaProductoId="+familiaProductoId);
            return familiaProductoId;
        }
        return null;
    }
    
    /**
     * Elimina un FamiliaProducto de la base de datos.
     * @param familiaProductoId El ID de la FamiliaProducto a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue.
     */
    @DeleteMapping("/familias_producto/borrar")
    public boolean borrarFamilia(@RequestParam("id") Integer familiaProductoId) {
        
        if (familiaProductoId != null && familiaProductoId != 0) {
            LOG.debug("deleteFamiliaProducto - clienteId="+familiaProductoId);
            return tpProductoSvc.deleteTipoProducto(familiaProductoId);
        }
        return false;
    }
}
