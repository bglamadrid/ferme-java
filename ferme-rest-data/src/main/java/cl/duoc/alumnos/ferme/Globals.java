package cl.duoc.alumnos.ferme;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public class Globals {
    
    /** Frecuencia de transacciones tras las cuales Hibernate 
     * sincroniza su caché de IDs contra las secuencias de la BD.
     * Se requiere reiniciar la aplicación para que esta variable surta efecto.
     **/
    public static final int ESPACIO_ASIGNACION_SECUENCIAS_HIBERNATE = 1;
    
    /* Parámetros de entidades, no declarados en la base de datos */
    public static char ORDEN_COMPRA_ESTADO_SOLICITADO = 'S';
    public static char ORDEN_COMPRA_ESTADO_RECEPCIONADO = 'R';
    public static char TIPO_VENTA_FACTURA = 'F';
    public static char TIPO_VENTA_BOLETA = 'B';
}
