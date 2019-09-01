package cl.duoc.alumnos.ferme.controllers;

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

import com.querydsl.core.types.Predicate;

import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.FamiliaProductoDTO;
import cl.duoc.alumnos.ferme.dto.ProductoDTO;
import cl.duoc.alumnos.ferme.dto.TipoProductoDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IFamiliasProductoService;
import cl.duoc.alumnos.ferme.services.interfaces.IProductosService;
import cl.duoc.alumnos.ferme.services.interfaces.ITiposProductoService;
import javassist.NotFoundException;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@RestController
@RequestMapping(FermeConfig.URI_BASE_REST_API+"/gestion")
public class ProductosController {
    private final static Logger LOG = LoggerFactory.getLogger(ProductosController.class);

    @Autowired private ITiposProductoService tpProductoSvc;
    @Autowired private IFamiliasProductoService fmlProductoSvc;
    @Autowired private IProductosService productoSvc;
    
    @GetMapping("/productos")
    public Collection<ProductoDTO> obtener(
        @RequestParam Map<String, String> allRequestParams
    ) {
        LOG.info("obtener sin pagina ni cantidad determinada");
        return this.obtener(null, null,  allRequestParams);
    }
    
    @GetMapping("/productos/{pageSize}")
    public Collection<ProductoDTO> obtener(
        @PathVariable Integer pageSize,
        @RequestParam Map<String, String> allRequestParams
    ) {
        LOG.info("obtener sin pagina determinada");
        return this.obtener(pageSize, null, allRequestParams);
    }
    
    @GetMapping("/productos/{pageSize}/{pageIndex}")
    public Collection<ProductoDTO> obtener(
        @PathVariable Integer pageSize,
        @PathVariable Integer pageIndex,
        @RequestParam Map<String, String> allRequestParams
    ) {
        LOG.info("obtener");
        
        Integer finalPageSize = FermeConfig.PAGINACION_REGISTROS_POR_PAGINA_INICIAL;
        Integer finalPageIndex = FermeConfig.PAGINACION_INDICE_INICIAL;
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
        
        LOG.info("obtener - "+finalPageSize+" registros; página "+finalPageIndex);
        LOG.debug("obtener - Filtros solicitados: "+filtros);
        Collection<ProductoDTO> productos = this.productoSvc.getProductos(finalPageSize, finalPageIndex, filtros);
        LOG.debug("obtener - productos.size()="+productos.size());
        LOG.info("obtener - Solicitud completa. Enviando respuesta al cliente.");
        return productos;
    }
    
    /**
     * Almacena un Producto nuevo o actualiza uno existente.
     * @param dto Un objeto DTO representando el Producto a almacenar/actualizar.
     * @return El ID del producto.
     * @throws NotFoundException Si no encuentra el tipo de producto
     */
    @PostMapping("/productos/guardar")
    public Integer guardar(
        @RequestBody ProductoDTO dto
    ) throws NotFoundException {
        LOG.info("guardar");
        if (dto != null) {
            LOG.debug("guardar - dto="+dto);
            Integer productoId =  productoSvc.saveProducto(dto);
            LOG.debug("guardar - productoId="+productoId);
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
    public boolean borrar(
        @RequestBody Integer productoId
    ) {
        LOG.info("borrar");
        if (productoId != null && productoId != 0) {
            LOG.debug("borrar - productoId="+productoId);
            return productoSvc.deleteProducto(productoId);
        }
        return false;
    }
    
    @GetMapping("/tipos_producto")
    public Collection<TipoProductoDTO> tipos(
        @RequestParam Map<String,String> allRequestParams
    ) {
        LOG.info("tipos");
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
     * @throws NotFoundException Si no se encuentra la familia de producto asociada
     */
    @PostMapping("/tipos_producto/guardar")
    public Integer guardarTipo(
        @RequestBody TipoProductoDTO dto
    ) throws NotFoundException {
        LOG.info("guardarTipo");
        if (dto != null) {
            LOG.debug("guardarTipo - dto="+dto);
            Integer tipoProductoId = tpProductoSvc.saveTipoProducto(dto);
            LOG.debug("guardarTipo - tipoProductoId="+tipoProductoId);
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
    public boolean borrarTipo(
        @RequestParam("id") Integer tipoProductoId
    ) {
        LOG.info("borrarTipo");
        if (tipoProductoId != null && tipoProductoId != 0) {
            LOG.debug("borrarTipo - clienteId="+tipoProductoId);
            return tpProductoSvc.deleteTipoProducto(tipoProductoId);
        }
        return false;
    }
    
    @GetMapping("/familias_producto")
    public Collection<FamiliaProductoDTO> familias(
        @RequestParam Map<String,String> allRequestParams
    ) {
        LOG.info("familias");
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
     * @throws NotFoundException Si no encuentra el rubro asociado.
     */
    @PostMapping("/familias_producto/guardar")
    public Integer guardarFamilia(
        @RequestBody FamiliaProductoDTO dto
    ) throws NotFoundException {
        LOG.info("guardarFamilia");
        if (dto != null) {
            LOG.debug("guardarFamilia - dto="+dto);
            Integer familiaProductoId = fmlProductoSvc.saveFamiliaProducto(dto);
            LOG.debug("guardarFamilia - familiaProductoId="+familiaProductoId);
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
    public boolean borrarFamilia(
        @RequestParam("id") Integer familiaProductoId
    ) {
        LOG.info("borrarFamilia");
        if (familiaProductoId != null && familiaProductoId != 0) {
            LOG.debug("deleteFamiliaProducto - clienteId="+familiaProductoId);
            return tpProductoSvc.deleteTipoProducto(familiaProductoId);
        }
        return false;
    }
}
