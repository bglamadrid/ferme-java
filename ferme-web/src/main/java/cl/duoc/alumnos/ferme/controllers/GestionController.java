/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.alumnos.ferme.controllers;

import cl.duoc.alumnos.ferme.dto.ProductoDTO;
import cl.duoc.alumnos.ferme.services.IProductosService;
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
public class GestionController {
    
    @Autowired private IProductosService productosSvc;
    
    @GetMapping("/hola")
    public String hola() {
        return "HOLA!";
    }
    
    @GetMapping("/productos/${pageSize}/${pageIndex}")
    public Collection<ProductoDTO> getProductos(
        @RequestParam Integer pageSize,
        @RequestParam Integer pageIndex
    ) {
        return this.productosSvc.getProductos(pageSize, pageIndex);
    }
}
