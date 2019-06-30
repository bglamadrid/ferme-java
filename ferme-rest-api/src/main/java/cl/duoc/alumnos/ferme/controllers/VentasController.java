package cl.duoc.alumnos.ferme.controllers;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.DetalleVentaDTO;
import cl.duoc.alumnos.ferme.dto.VentaDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IVentasService;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@RestController
@RequestMapping(FermeConfig.URI_BASE_REST_API+"/gestion/ventas")
public class VentasController {
    private final static Logger LOG = LoggerFactory.getLogger(VentasController.class);
    
    @Autowired private IVentasService ventaSvc;
    
    @GetMapping("/next")
    public Integer obtenerSiguienteId() {
        return ventaSvc.getNextId();
    }
    
    @GetMapping("")
    public Collection<VentaDTO> obtener(
        @RequestParam Map<String,String> allRequestParams
    ) {
        LOG.info("obtener sin pagina ni cantidad determinada");  
        return this.obtener(null, null, allRequestParams);
    }
    
    @GetMapping("/{pageSize}")
    public Collection<VentaDTO> obtener(
        @RequestParam Integer pageSize,
        @RequestParam Map<String, String> allRequestParams
    ) {
        LOG.info("obtener sin pagina determinada"); 
        return this.obtener(pageSize, null, allRequestParams);
    }
    
    @GetMapping("/{pageSize}/{pageIndex}")
    public Collection<VentaDTO> obtener(
        @RequestParam Integer pageSize,
        @RequestParam Integer pageIndex,
        @RequestParam Map<String,String> allRequestParams
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
            filtros = ventaSvc.queryParamsMapToVentasFilteringPredicate(allRequestParams);
        }
        
        LOG.info("obtener - "+finalPageSize+" registros; página "+finalPageIndex);
        LOG.debug("obtener - Filtros solicitados: "+filtros);
        Collection<VentaDTO> ventas = this.ventaSvc.getVentas(finalPageSize, finalPageIndex, filtros);
        LOG.debug("obtener - ventas.size()="+ventas.size());
        LOG.info("obtener - Solicitud completa. Enviando respuesta al cliente.");
        return ventas;
    }
    
    /**
     * Obtiene los detalles de una orden de compra.
     * @param dto Un objeto DTO representando la Orden de Compra a consultar.
     * @return Una colección de objetos DTO.
     */
    @PostMapping("/detalles")
    public Collection<DetalleVentaDTO> detalles(
        @RequestBody VentaDTO dto
    ) {
        LOG.info("detalles"); 
        if (dto != null && dto.getIdVenta() != null && dto.getIdVenta() != 0) {
            LOG.debug("getDetallesVenta - dto="+dto.toString());
            return ventaSvc.getDetallesVenta(dto.getIdVenta());
        }
        return null;
    }
    
    /**
     * Almacena una Venta nueva o actualiza una existente.
     * @param dto Un objeto DTO representando la Venta a almacenar/actualizar.
     * @return El ID de la venta.
     * @throws NotFoundException si el cliente asociado a la venta no existe, o alguno de los productos vendidos no existe
     */
    @PostMapping("/guardar")
    public Integer guardar(
        @RequestBody VentaDTO dto
    ) throws NotFoundException {
        LOG.info("guardar"); 
        if (dto != null) {
            LOG.debug("guardar - dto="+dto);
            Integer ventaId = ventaSvc.saveVenta(dto);
            LOG.debug("guardar - ventaId="+ventaId);
            return ventaId;
        }
        return null;
    }
    
    /**
     * Elimina una Venta de la base de datos.
     * @param ventaId El ID de la Venta a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue.
     */
    @PostMapping("/borrar")
    public boolean borrar(
        @RequestBody Integer ventaId
    ) {
        LOG.info("borrar"); 
        if (ventaId != null && ventaId != 0) {
            LOG.debug("borrar - ventaId="+ventaId);
            return ventaSvc.deleteVenta(ventaId);
        }
        return false;
    }
}
