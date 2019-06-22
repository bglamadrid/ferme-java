package cl.duoc.alumnos.ferme.util;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.FermeConfig;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public class FermeDates {    

    public static final Date fechaStringToDate(String stringFecha) {
        Date fecha; 
        DateFormat formateador = new SimpleDateFormat(FermeConfig.DEFAULT_DATE_FORMAT);
        try {
            fecha = formateador.parse(stringFecha);
        } catch (ParseException exc) {
            fecha = null;
        }
        
        return fecha;
    }
    
    public static final String fechaToString(Date fecha) {
        DateFormat formateador = new SimpleDateFormat(FermeConfig.DEFAULT_DATE_FORMAT);
        final String stringFecha = formateador.format(fecha);
        return stringFecha;
    }
}
