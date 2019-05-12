package cl.duoc.alumnos.ferme.controllers;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.dto.DetalleOrdenCompraDTO;
import cl.duoc.alumnos.ferme.dto.OrdenCompraDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IOrdenesCompraService;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
public class OrdenesCompraController {
    
    @Autowired private IOrdenesCompraService ordenCompraSvc;
    
    @GetMapping("/ordenes_compra")
    public Collection<OrdenCompraDTO> getOrdenesCompra(
        @RequestParam Map<String,String> allRequestParams
    ) {
        return this.getOrdenesCompra(null, null, allRequestParams);
    }
    
    @GetMapping("/ordenes_compra/{pageSize}")
    public Collection<OrdenCompraDTO> getOrdenesCompra(
        @RequestParam Integer pageSize,
        @RequestParam Map<String, String> allRequestParams
    ) {
        return this.getOrdenesCompra(pageSize, null, allRequestParams);
    }
    
    @GetMapping("/ordenes_compra/{pageSize}/{pageIndex}")
    public Collection<OrdenCompraDTO> getOrdenesCompra(
        @RequestParam Integer pageSize,
        @RequestParam Integer pageIndex,
        @RequestParam Map<String,String> allRequestParams
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
            condiciones = ordenCompraSvc.queryParamsMapToOrdenesCompraFilteringPredicate(allRequestParams);
        }
        
        return ordenCompraSvc.getOrdenesCompra(finalPageSize, finalPageIndex, condiciones);
    }
    
    /**
     * Obtiene los detalles de una orden de compra.
     * @param dto Un objeto DTO representando la Orden de Compra a consultar.
     * @return Una colección de objetos DTO.
     */
    @PostMapping("/ordenes_compra/detalles")
    public Collection<DetalleOrdenCompraDTO> getDetallesOrdenCompra(@RequestBody OrdenCompraDTO dto) {
        
        if (dto != null && dto.getIdOrdenCompra() != null && dto.getIdOrdenCompra() != 0) {
            return ordenCompraSvc.getDetallesOrdenCompra(dto.getIdOrdenCompra());
        }
        return null;
    }
    
    /**
     * Almacena una Orden de Compra nueva o actualiza una existente.
     * @param dto Un objeto DTO representando la Orden de Compra a almacenar/actualizar.
     * @return El ID de la venta.
     */
    @PostMapping("/ordenes_compra/guardar")
    public Integer saveOrdenCompra(@RequestBody OrdenCompraDTO dto) {
        
        if (dto != null) {
            return ordenCompraSvc.saveOrdenCompra(dto);
        }
        return null;
    }
    
    /**
     * Elimina una Orden de Compra de la base de datos.
     * @param ventaId El ID de la Orden de Compra a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue.
     */
    @PostMapping("/ordenes_compra/borrar")
    public boolean deleteOrdenCompra(@RequestBody Integer ventaId) {
        
        if (ventaId != null && ventaId != 0) {
            return ordenCompraSvc.deleteOrdenCompra(ventaId);
        }
        return false;
    }
}
