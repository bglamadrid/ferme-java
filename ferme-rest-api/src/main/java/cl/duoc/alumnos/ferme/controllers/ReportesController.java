package cl.duoc.alumnos.ferme.controllers;

import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.services.interfaces.IReportesService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(FermeConfig.URI_BASE_REST_API+"/gestion/reportes")
public class ReportesController {
    
    @Autowired private IReportesService reporteSvc;
    private final static Logger LOG = LoggerFactory.getLogger(ReportesController.class);
    
    @GetMapping("/ventas_realizadas")
    public Long obtenerVentasRealizadas(@RequestParam Map<String,String> allRequestParams) {
        
        return this.reporteSvc.obtenerTotalVentasRealizadas(allRequestParams);
    }
    
}
