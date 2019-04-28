/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.alumnos.ferme.controllers.gestion;

import cl.duoc.alumnos.ferme.FermeParams;
import cl.duoc.alumnos.ferme.dto.FamiliaProductoDTO;
import cl.duoc.alumnos.ferme.dto.ProductoDTO;
import cl.duoc.alumnos.ferme.dto.RubroDTO;
import cl.duoc.alumnos.ferme.dto.TipoProductoDTO;
import cl.duoc.alumnos.ferme.services.IFamiliasProductoService;
import cl.duoc.alumnos.ferme.services.IProductosService;
import cl.duoc.alumnos.ferme.services.IRubrosService;
import cl.duoc.alumnos.ferme.services.ITiposProductoService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    @Autowired private ITiposProductoService tpProductoSvc;
    @Autowired private IFamiliasProductoService fmlProductoSvc;
    @Autowired private IProductosService productoSvc;
    @Autowired private IRubrosService rubroSvc;
    
    @GetMapping("/productos/")
    public Collection<ProductoDTO> getVentas() {
        return this.productoSvc.getProductos(FermeParams.DEFAULT_PAGE_SIZE, FermeParams.DEFAULT_PAGE_INDEX);
    }
    
    @GetMapping("/productos/{pageSize}/{pageIndex}")
    public Collection<ProductoDTO> getProductos(
        @RequestParam Integer pageSize,
        @RequestParam Integer pageIndex
    ) {
        return this.productoSvc.getProductos(pageSize, pageIndex);
    }
}
