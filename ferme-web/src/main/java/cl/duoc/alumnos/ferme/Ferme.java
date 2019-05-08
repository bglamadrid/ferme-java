package cl.duoc.alumnos.ferme;

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
    private final static Logger LOG = LoggerFactory.getLogger(Ferme.class);
    
    public final static int DEFAULT_PAGE_INDEX = 0;
    public final static int DEFAULT_PAGE_SIZE = 10;
    
    public final static int CARGO_VENDEDOR_ID = 1;
    public final static int CARGO_ENCARGADO_ID = 2;
    public final static int CARGO_ADMINISTRADOR_ID = 3;
    
    public final static char TIPO_VENTA_BOLETA = 'B';
    public final static char TIPO_VENTA_FACTURA = 'F';
    
    public final static char ORDEN_COMPRA_ESTADO_SOLICITADO = 'B';
    public final static char ORDEN_COMPRA_ESTADO_RECEPCIONADO = 'R';
        
    public static void main(String[] args) {
        SpringApplication.run(FermeConfig.class, args);
        LOG.info("Aplicación FERME cargada e iniciada exitosamente.");
    }
    
}
