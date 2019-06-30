package cl.duoc.alumnos.ferme;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
import cl.duoc.alumnos.ferme.domain.entities.*;

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
    
    /** URI base para el consumo de la API REST. 
     * Se requiere reiniciar la aplicación para que esta variable surta efecto.
     **/
    public static final String URI_BASE_REST_API = "/api/";
    
    /** Formato de fecha estándar a nivel de aplicación.
     * Se requiere reiniciar la aplicación para que esta variable surta efecto.
     **/
    public static final String FORMATO_FECHA = "dd/MM/yyyy";
    
    /** Frecuencia de transacciones tras las cuales Hibernate 
     * sincroniza su caché de IDs contra las secuencias de la BD.
     * Se requiere reiniciar la aplicación para que esta variable surta efecto.
     **/
    public static final int ESPACIO_ASIGNACION_SECUENCIAS_HIBERNATE = 1;
    
    /** Verificar disponibilidad de algoritmos según servidor de despliegue.
     * Se requiere reiniciar la aplicación para que esta variable surta efecto.
     **/
    public static final String ALGORITMO_CRIPTOGRAFICO_GLOBAL = "SHA-256";
    
    /** Tiempo de vigencia de las sesiones, en milisegundos.
     * Se requiere reiniciar la aplicación para que esta variable surta efecto.
     **/
    public static final long DURACION_SESION = 600000L;
    
    
    /** Índice por defecto de página a consultar en los mantenedores.
     **/
    public static int PAGINACION_INDICE_INICIAL = 0;
    
    /** Cantidad por defecto de registros por página a listar en los mantenedores.
     **/
    public static int PAGINACION_REGISTROS_POR_PAGINA_INICIAL = 10;
    
    
    /** Columnas por defecto a usar como criterio de orden en los mantenedores.
     **/
    public final static Map<Class, String> COLUMNAS_ORDENAMIENTO_MAPA = generarMapaDeColumnasDeOrdenPorDefecto();
    
    /** Cargos que existen en la aplicación.
     */
    public final static Map<String, Integer> CARGOS_MAP = createCargosMap();
    
    
    /* Parámetros de entidades, no declarados en la base de datos */
    public static char ORDEN_COMPRA_ESTADO_SOLICITADO = 'S';
    public static char ORDEN_COMPRA_ESTADO_RECEPCIONADO = 'R';
    public static char TIPO_VENTA_FACTURA = 'F';
    public static char TIPO_VENTA_BOLETA = 'B';

    
    @Autowired private DataSource ds;
    
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
    
    private static Map<Class, String> generarMapaDeColumnasDeOrdenPorDefecto() {
        Map<Class, String> columnasOrdenPorDefecto = new HashMap<>();
        columnasOrdenPorDefecto.put(Producto.class, "_id");
        columnasOrdenPorDefecto.put(DetalleOrdenCompra.class, "_id");
        columnasOrdenPorDefecto.put(Rubro.class, "_id");
        columnasOrdenPorDefecto.put(Empleado.class, "_id");
        columnasOrdenPorDefecto.put(Usuario.class, "_id");
        columnasOrdenPorDefecto.put(FamiliaProducto.class, "_id");
        columnasOrdenPorDefecto.put(DetalleVenta.class, "_id");
        columnasOrdenPorDefecto.put(OrdenCompra.class, "_id");
        columnasOrdenPorDefecto.put(TipoProducto.class, "_id");
        columnasOrdenPorDefecto.put(Proveedor.class, "_id");
        columnasOrdenPorDefecto.put(Cliente.class, "_id");
        columnasOrdenPorDefecto.put(Venta.class, "_id");
        columnasOrdenPorDefecto.put(Cargo.class, "_id");
        columnasOrdenPorDefecto.put(Persona.class, "_id");
        return columnasOrdenPorDefecto;
    }
    
    private static Map<String, Integer> createCargosMap() {
        Map<String, Integer> cargosMap = new HashMap<>();
        cargosMap.put("Vendedor", 51);
        cargosMap.put("Encargado", 52);
        cargosMap.put("Administrador", 53);
        return new HashMap<>();
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        
        registry.addMapping(URI_BASE_REST_API+"*") // permitimos que la API se pueda consumir...
                .allowedOrigins("http://localhost:4200") // desde app Angular local
                .allowedMethods("*");
    }
    
}
