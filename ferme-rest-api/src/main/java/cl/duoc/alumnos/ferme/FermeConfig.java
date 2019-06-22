package cl.duoc.alumnos.ferme;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories
@ComponentScan
@ImportResource("beans.xml")
public class FermeConfig implements WebMvcConfigurer { 

    /* REQUIEREN REINICIO */
    
    /** URI base para el consumo de la API. **/
    public static final String API_BASE_URI = "/api/";
    
    /** Formato de fecha estándar a nivel de aplicación. **/
    public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    
    /** Frecuencia de transacciones tras las cuales Hibernate 
     * sincroniza su caché de IDs contra las secuencias de la BD. **/
    public static final int DEFAULT_HIBERNATE_SEQUENCES_ALLOCATION_SIZE = 1;
    
    /** Verificar disponibilidad de algoritmos según servidor de despliegue. **/
    public static final String DEFAULT_HASHING_ALGORITHM = "SHA-256";
    
    /** Tiempo de vigencia de las sesiones, en milisegundos. **/
    public static final long SESSION_DURATION = 600000L;
    
    
    /* No requieren reinicio */
    
    public static int DEFAULT_PAGE_INDEX = 0;
    public static int DEFAULT_PAGE_SIZE = 10;
    
    public static String PRODUCTO_DEFAULT_SORT_COLUMN = "_id";
    public static String DETALLE_ORDEN_COMPRA_DEFAULT_SORT_COLUMN = "_id";
    public static String RUBRO_DEFAULT_SORT_COLUMN = "_id";
    public static String EMPLEADO_DEFAULT_SORT_COLUMN = "_id";
    public static String USUARIO_DEFAULT_SORT_COLUMN = "_id";
    public static String FAMILIA_PRODUCTO_DEFAULT_SORT_COLUMN = "_id";
    public static String DETALLE_VENTA_DEFAULT_SORT_COLUMN = "_id";
    public static String ORDEN_COMPRA_DEFAULT_SORT_COLUMN = "_id";
    public static String TIPO_PRODUCTO_DEFAULT_SORT_COLUMN = "_id";
    public static String PROVEEDOR_DEFAULT_SORT_COLUMN = "_id";
    public static String CLIENTE_DEFAULT_SORT_COLUMN = "_id";
    public static String VENTA_DEFAULT_SORT_COLUMN = "_id";
    public static String CARGO_DEFAULT_SORT_COLUMN = "_descripcion";
    public static String PERSONA_DEFAULT_SORT_COLUMN = "_nombreCompleto";
    
    /* Parámetros de entidades, no declarados en la base de datos */
    
    public static char ORDEN_COMPRA_ESTADO_SOLICITADO = 'S';
    public static char ORDEN_COMPRA_ESTADO_RECEPCIONADO = 'R';
    
    public static char TIPO_VENTA_FACTURA = 'F';
    public static char TIPO_VENTA_BOLETA = 'B';
    
    /* Inmutables: parámetros declarados en la base de datos */
    
    public final static Map<String, Integer> cargos = createCargosMap();

    
    
    
    @Autowired private DataSource ds;
    
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }
    
    private static Map<String, Integer> createCargosMap() {
        Map<String, Integer> cargosMap = new HashMap<>();
        cargosMap.put("Vendedor", 1);
        cargosMap.put("Encargado", 2);
        cargosMap.put("Administrador", 3);
        return new HashMap<>();
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        
        registry.addMapping(API_BASE_URI+"*") // permitimos que la API se pueda consumir...
                .allowedOrigins("http://localhost:4200") // desde app Angular local
                .allowedMethods("*");
    }
    
}
