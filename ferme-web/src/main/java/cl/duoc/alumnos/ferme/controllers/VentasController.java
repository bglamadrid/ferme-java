package cl.duoc.alumnos.ferme.controllers;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.dto.VentaDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IVentasService;
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
public class VentasController {
    
    @Autowired private IVentasService ventaSvc;
    
    @GetMapping("/ventas/")
    public Collection<VentaDTO> getVentas(
        @RequestParam Map<String,String> allRequestParams
    ) {
        return this.getVentas(null, null, allRequestParams);
    }
    
    @GetMapping("/ventas/{pageSize}")
    public Collection<VentaDTO> getVentas(
        @RequestParam Integer pageSize,
        @RequestParam Map<String, String> allRequestParams
    ) {
        return this.getVentas(pageSize, null, allRequestParams);
    }
    
    @GetMapping("/ventas/{pageSize}/{pageIndex}")
    public Collection<VentaDTO> getVentas(
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
            condiciones = ventaSvc.queryParamsMapToVentasFilteringPredicate(allRequestParams);
        }
        
        return ventaSvc.getVentas(finalPageSize, finalPageIndex, condiciones);
    }
    
    /**
     * Almacena una Venta nueva o actualiza una existente.
     * @param dto Un objeto DTO representando la Venta a almacenar/actualizar.
     * @return El ID de la venta.
     */
    @PostMapping("/ventas/guardar")
    public Integer saveVenta(@RequestBody VentaDTO dto) {
        
        if (dto != null) {
            return ventaSvc.saveVenta(dto);
        }
        return null;
    }
    
    /**
     * Elimina una Venta de la base de datos.
     * @param ventaId El ID de la Venta a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue.
     */
    @PostMapping("/ventas/guardar")
    public boolean deleteVenta(@RequestBody Integer ventaId) {
        
        if (ventaId != null && ventaId != 0) {
            return ventaSvc.deleteVenta(ventaId);
        }
        return false;
    }
}
