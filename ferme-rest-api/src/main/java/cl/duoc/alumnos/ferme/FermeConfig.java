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

    public static char TIPO_VENTA_FACTURA = 'F';
    public static String DETALLE_ORDEN_COMPRA_DEFAULT_SORT_COLUMN = "_id";
    public static String RUBRO_DEFAULT_SORT_COLUMN = "_id";
    public static char ORDEN_COMPRA_ESTADO_SOLICITADO = 'S';
    /* Globales mutables */
    /** Paginación */
    public static int DEFAULT_PAGE_INDEX = 0;
    public static String EMPLEADO_DEFAULT_SORT_COLUMN = "_id";
    public static String USUARIO_DEFAULT_SORT_COLUMN = "_id";
    public static String FAMILIA_PRODUCTO_DEFAULT_SORT_COLUMN = "_id";
    public static String DETALLE_VENTA_DEFAULT_SORT_COLUMN = "_id";
    public static String ORDEN_COMPRA_DEFAULT_SORT_COLUMN = "_id";
    public static final int DEFAULT_HIBERNATE_SEQUENCES_ALLOCATION_SIZE = 1;
    public static int DEFAULT_PAGE_SIZE = 10;
    public static String TIPO_PRODUCTO_DEFAULT_SORT_COLUMN = "_id";
    /** Parámetros de entidades, no declarados en la base de datos */
    public static char TIPO_VENTA_BOLETA = 'B';
    public static final String DEFAULT_HASHING_ALGORITHM = "SHA-256";
    /** En milisegundos. **/
    public static final long SESSION_DURATION = 600000L;
    public static String PROVEEDOR_DEFAULT_SORT_COLUMN = "_id";
    public static String CLIENTE_DEFAULT_SORT_COLUMN = "_id";
    public static String VENTA_DEFAULT_SORT_COLUMN = "_id";
    /* Globales inmutables: parámetros declarados en la base de datos */
    public static Map<String, Integer> cargos = createCargosMap();
    public static String CARGO_DEFAULT_SORT_COLUMN = "_descripcion";
    public static String PERSONA_DEFAULT_SORT_COLUMN = "_nombreCompleto";
    public static char ORDEN_COMPRA_ESTADO_RECEPCIONADO = 'R';
    /* Variables que requieren reiniciar */
    public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    /** Configuración de orden por defecto */
    public static String PRODUCTO_DEFAULT_SORT_COLUMN = "_id";

    private static Map<String, Integer> createCargosMap() {
        Map<String, Integer> cargosMap = new HashMap<>();
        cargosMap.put("Vendedor", 1);
        cargosMap.put("Encargado", 2);
        cargosMap.put("Administrador", 3);
        return new HashMap<>();
    }
    
    @Autowired private DataSource ds;
    
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }
    
}
