package cl.duoc.alumnos.ferme.services.interfaces;

import java.util.Map;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public interface IReportesService {
    
    public Long obtenerTotalVentasRealizadas(Map<String,String> allRequestParams);
    
    public Long obtenerUnidadesProductoVendidas(Map<String,String> allRequestParams);
    
}
