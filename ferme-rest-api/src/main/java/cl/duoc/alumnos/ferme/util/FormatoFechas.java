package cl.duoc.alumnos.ferme.util;

import cl.duoc.alumnos.ferme.FermeConfig;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public class FormatoFechas {    

    public static final Date stringADateLocal(String stringFecha) {
        Date fecha; 
        DateFormat formateador = new SimpleDateFormat(FermeConfig.FORMATO_FECHA);
        try {
            fecha = formateador.parse(stringFecha);
        } catch (ParseException exc) {
            fecha = null;
        }
        
        return fecha;
    }
    
    public static final String dateAStringLocal(Date fecha) {
        DateFormat formateador = new SimpleDateFormat(FermeConfig.FORMATO_FECHA);
        final String stringFecha = formateador.format(fecha);
        return stringFecha;
    }
}
