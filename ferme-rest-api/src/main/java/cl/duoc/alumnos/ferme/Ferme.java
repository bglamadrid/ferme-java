package cl.duoc.alumnos.ferme;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author Benjamin Guillermo
 */
@SpringBootApplication
@ComponentScan("cl.duoc.alumnos.ferme.*")
@EntityScan("cl.duoc.alumnos.ferme.domain.entities")
public class Ferme implements WebMvcConfigurer {
    private final static Logger LOG = LoggerFactory.getLogger(Ferme.class);
    
        
    public static void main(String[] args) {
        SpringApplication.run(FermeConfig.class, args);
        LOG.info("Aplicación FERME cargada e iniciada exitosamente.");
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/gestion/*").allowedOrigins("http://localhost:4200").allowedMethods("*");
    }
    
    
}
