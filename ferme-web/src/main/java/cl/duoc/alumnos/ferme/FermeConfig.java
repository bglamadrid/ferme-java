package cl.duoc.alumnos.ferme;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories
@ComponentScan
@ImportResource("beans.xml")
public class FermeConfig { 
    
    @Autowired
    private DataSource ds;
    
}
