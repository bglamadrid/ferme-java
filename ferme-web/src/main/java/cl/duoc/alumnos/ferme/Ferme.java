package cl.duoc.alumnos.ferme;

import cl.duoc.alumnos.ferme.controllers.ComunController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author Benjamin Guillermo
 */
@SpringBootApplication
@ComponentScan("cl.duoc.alumnos.ferme.*")
@EntityScan("cl.duoc.alumnos.ferme.domain.entities")
public class Ferme {
    private final static Logger LOG = LoggerFactory.getLogger(ComunController.class);
        
    public static void main(String[] args) {
        SpringApplication.run(FermeConfig.class, args);
        LOG.info("Aplicación FERME cargada e iniciada exitosamente.");
    }
    
}
