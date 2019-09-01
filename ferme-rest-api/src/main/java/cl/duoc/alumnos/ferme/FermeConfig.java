package cl.duoc.alumnos.ferme;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import cl.duoc.alumnos.ferme.entities.Cargo;
import cl.duoc.alumnos.ferme.entities.Cliente;
import cl.duoc.alumnos.ferme.entities.DetalleOrdenCompra;
import cl.duoc.alumnos.ferme.entities.DetalleVenta;
import cl.duoc.alumnos.ferme.entities.Empleado;
import cl.duoc.alumnos.ferme.entities.FamiliaProducto;
import cl.duoc.alumnos.ferme.entities.OrdenCompra;
import cl.duoc.alumnos.ferme.entities.Persona;
import cl.duoc.alumnos.ferme.entities.Producto;
import cl.duoc.alumnos.ferme.entities.Proveedor;
import cl.duoc.alumnos.ferme.entities.Rubro;
import cl.duoc.alumnos.ferme.entities.TipoProducto;
import cl.duoc.alumnos.ferme.entities.Usuario;
import cl.duoc.alumnos.ferme.entities.Venta;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Configuration
@ComponentScan
public class FermeConfig implements WebMvcConfigurer { 
    
    /** URI base para el consumo de la API REST. 
     * Se requiere reiniciar la aplicación para que esta variable surta efecto.
     **/
    public static final String URI_BASE_REST_API = "/api";
    
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
    @SuppressWarnings("rawtypes")
	public final static Map<Class, String> COLUMNAS_ORDENAMIENTO_MAPA = generarMapaDeColumnasDeOrdenPorDefecto();
    
    /** Cargos que existen en la aplicación.
     */
    public final static Map<String, Integer> CARGOS_MAP = createCargosMap();
    

    
    
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
    
    @SuppressWarnings("rawtypes")
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
        
        registry.addMapping(URI_BASE_REST_API+"/*") // permitimos que la API se pueda consumir...
                .allowedOrigins("http://localhost:4200") // desde app Angular local
                .allowedMethods("*");
    }
    
}
