package cl.duoc.alumnos.ferme.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.services.interfaces.IReportesService;

@RestController
@RequestMapping(FermeConfig.URI_BASE_REST_API+"/gestion/reportes")
public class ReportesController {
    private final static Logger LOG = LoggerFactory.getLogger(ReportesController.class);
    
    @Autowired private IReportesService reporteSvc;
    
    @GetMapping("/ventas_realizadas")
    public Long obtenerVentasRealizadas(
        @RequestParam Map<String,String> allRequestParams
    ) {
        LOG.info("obtenerVentasRealizadas");  
        return this.reporteSvc.obtenerTotalVentasRealizadas(allRequestParams);
    }
    
}
