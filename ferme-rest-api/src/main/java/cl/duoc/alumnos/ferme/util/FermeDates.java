package cl.duoc.alumnos.ferme.util;

import cl.duoc.alumnos.ferme.Ferme;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author got12
 */
public class FermeDates {
    
    private static final Logger LOG = LoggerFactory.getLogger(FermeDates.class);
    

    public static final Date fechaStringToDate(String stringFecha) {
        Date fecha; 
        try {
            DateFormat formateador = new SimpleDateFormat(Ferme.DEFAULT_DATE_FORMAT);
            fecha = formateador.parse(stringFecha);
        } catch (Exception exc) {
            LOG.warn("FermeDates.fechaStringToDate(): La fecha ingresada tiene un formato inválido, no se pudo convertir y es null.", exc);
            fecha = null;
        }
        
        return fecha;
    }
    
    public static final String fechaToString(Date fecha) {
        DateFormat formateador = new SimpleDateFormat(Ferme.DEFAULT_DATE_FORMAT);
        final String stringFecha = formateador.format(fecha);
        return stringFecha;
    }
}
