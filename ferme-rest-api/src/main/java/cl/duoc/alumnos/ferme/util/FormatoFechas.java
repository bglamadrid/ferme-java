package cl.duoc.alumnos.ferme.util;

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
public class FormatoFechas {    
    private final static Logger LOG = LoggerFactory.getLogger(FormatoFechas.class);

    public static final Date stringADateLocal(String stringFecha) {
        LOG.info("stringADateLocal");
        Date fecha; 
        DateFormat formateador = new SimpleDateFormat(FermeConfig.FORMATO_FECHA);
        try {
            fecha = formateador.parse(stringFecha);
            LOG.debug("stringADateLocal - fecha="+fecha);
        } catch (ParseException exc) {
            fecha = null;
            LOG.warn("stringADateLocal - fecha=null");
        }
        
        return fecha;
    }
    
    public static final String dateAStringLocal(Date fecha) {
        LOG.info("dateAStringLocal");
        DateFormat formateador = new SimpleDateFormat(FermeConfig.FORMATO_FECHA);
        final String stringFecha = formateador.format(fecha);
        LOG.debug("dateAStringLocal - stringFecha="+stringFecha);
        return stringFecha;
    }
}
