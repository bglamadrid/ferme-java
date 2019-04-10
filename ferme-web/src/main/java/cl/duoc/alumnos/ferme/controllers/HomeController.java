package cl.duoc.alumnos.ferme.controllers;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Benjamin Guillermo
 */
@RestController
@ComponentScan
public class HomeController {
    
    @GetMapping("/")
    public String hola() {
        return "HOLA!";
    }
}
