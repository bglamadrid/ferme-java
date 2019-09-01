package cl.duoc.alumnos.ferme.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public abstract class FormatoFechas {    

    public static final Date stringADateLocal(String stringFecha) {
        Date fecha; 
        DateFormat formateador = new SimpleDateFormat(Constantes.FORMATO_FECHA);
        try {
            fecha = formateador.parse(stringFecha);
        } catch (ParseException exc) {
            fecha = null;
        }
        
        return fecha;
    }
    
    public static final String dateAStringLocal(Date fecha) {
        DateFormat formateador = new SimpleDateFormat(Constantes.FORMATO_FECHA);
        return formateador.format(fecha);
    }
}
