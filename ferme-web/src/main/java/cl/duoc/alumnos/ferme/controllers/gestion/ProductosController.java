package cl.duoc.alumnos.ferme.controllers.gestion;

import cl.duoc.alumnos.ferme.FermeParams;
import cl.duoc.alumnos.ferme.dto.FamiliaProductoDTO;
import cl.duoc.alumnos.ferme.dto.ProductoDTO;
import cl.duoc.alumnos.ferme.dto.TipoProductoDTO;
import cl.duoc.alumnos.ferme.services.IFamiliasProductoService;
import cl.duoc.alumnos.ferme.services.IProductosService;
import cl.duoc.alumnos.ferme.services.ITiposProductoService;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    
    
    @GetMapping({"/tipos_producto", "/tipos_producto/"})
    public Collection<TipoProductoDTO> getTiposProducto() {
        return this.tpProductoSvc.getTiposProductos();
    }

    
    @GetMapping({"/familias_producto", "/familias_producto/"})
    public Collection<FamiliaProductoDTO> getFamiliasProducto() {
        return this.fmlProductoSvc.getFamiliasProductos();
    }

    
    @GetMapping({"/productos"})
    public Collection<ProductoDTO> getProductos(
            @RequestParam Map<String, String> allRequestParams
    ) {
        return this.getProductos(null, null,  allRequestParams);
    }

    
    @GetMapping({"/productos/{pageSize}"})
    public Collection<ProductoDTO> getProductos(
            @PathVariable Integer pageSize,
            @RequestParam Map<String, String> allRequestParams
    ) {
        return this.getProductos(pageSize, null, allRequestParams);
    }
    
    @GetMapping({"/productos/{pageSize}/{pageIndex}"})
    public Collection<ProductoDTO> getProductos(
            @PathVariable Integer pageSize,
            @PathVariable Integer pageIndex,
            @RequestParam Map<String, String> allRequestParams
    ) {
        Integer finalPageSize = FermeParams.DEFAULT_PAGE_SIZE;
        Integer finalPageIndex = FermeParams.DEFAULT_PAGE_INDEX;
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
}
