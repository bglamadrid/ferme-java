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
    
    /* Variables que requieren reiniciar */
    public final static String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    public final static String DEFAULT_HASHING_ALGORITHM = "SHA-256";
    /** En segundos. **/
    public final static long SESSION_DURATION = 600L;
    public final static int DEFAULT_HIBERNATE_SEQUENCES_ALLOCATION_SIZE = 1;
    
    /* Globales mutables */
    /** Paginación */
    public static int DEFAULT_PAGE_INDEX = 0;
    public static int DEFAULT_PAGE_SIZE = 10;
    
    /** Configuración de orden por defecto */
    public static String PRODUCTO_DEFAULT_SORT_COLUMN = "_id";
    public static String TIPO_PRODUCTO_DEFAULT_SORT_COLUMN = "_id";
    public static String FAMILIA_PRODUCTO_DEFAULT_SORT_COLUMN = "_id";
    public static String RUBRO_DEFAULT_SORT_COLUMN = "_id";
    public static String PROVEEDOR_DEFAULT_SORT_COLUMN = "_id";
    public static String ORDEN_COMPRA_DEFAULT_SORT_COLUMN = "_id";
    public static String DETALLE_ORDEN_COMPRA_DEFAULT_SORT_COLUMN = "_id";
    public static String VENTA_DEFAULT_SORT_COLUMN = "_id";
    public static String DETALLE_VENTA_DEFAULT_SORT_COLUMN = "_id";
    public static String PERSONA_DEFAULT_SORT_COLUMN = "_nombreCompleto";  
    public static String USUARIO_DEFAULT_SORT_COLUMN = "_id";
    public static String CLIENTE_DEFAULT_SORT_COLUMN = "_id";
    public static String EMPLEADO_DEFAULT_SORT_COLUMN = "_id";
    public static String CARGO_DEFAULT_SORT_COLUMN = "_descripcion";  
    
    /** Parámetros de entidades, no declarados en la base de datos */
    public static char TIPO_VENTA_BOLETA = 'B';
    public static char TIPO_VENTA_FACTURA = 'F';
    public static char ORDEN_COMPRA_ESTADO_SOLICITADO = 'S';
    public static char ORDEN_COMPRA_ESTADO_RECEPCIONADO = 'R';
    
    /* Globales inmutables: parámetros declarados en la base de datos */
    public static Map<String, Integer> cargos = createCargosMap();
        
    public static void main(String[] args) {
        SpringApplication.run(FermeConfig.class, args);
        LOG.info("Aplicación FERME cargada e iniciada exitosamente.");
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/gestion/*").allowedOrigins("http://localhost:4200").allowedMethods("*");
    }
    
    private static Map<String, Integer> createCargosMap(){
        Map<String, Integer> cargosMap = new HashMap<>();
        cargosMap.put("Vendedor", 1);
        cargosMap.put("Encargado", 2);
        cargosMap.put("Administrador", 3);
        return new HashMap<>();
    }
    
}
