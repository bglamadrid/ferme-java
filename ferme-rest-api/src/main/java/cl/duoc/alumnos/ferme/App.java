package cl.duoc.alumnos.ferme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@SpringBootApplication
@ComponentScan("cl.duoc.alumnos.ferme.*")
public class App {
    private final static Logger LOG = LoggerFactory.getLogger(App.class);
    
    /**
     * Inicia la aplicación.
     * @param args No hay argumentos aceptados aún.
     */
    public static void main(String[] args) {
        SpringApplication.run(FermeConfig.class, args);
        LOG.info("Aplicación FERME cargada e iniciada exitosamente.");
    }
    
}
