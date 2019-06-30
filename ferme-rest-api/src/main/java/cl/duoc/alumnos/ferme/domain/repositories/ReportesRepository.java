package cl.duoc.alumnos.ferme.domain.repositories;

import cl.duoc.alumnos.ferme.domain.entities.Producto;
import cl.duoc.alumnos.ferme.util.FormatoFechas;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author got12
 */
@Repository
public class ReportesRepository implements IReportesRepository {
    private Logger LOG = LoggerFactory.getLogger(ReportesRepository.class);

    @Autowired private EntityManager entityManager;

    @Override
    public Long findVentasRealizadas() {
        return this.findVentasRealizadas(null, null);
    }

    @Override
    public Long findVentasRealizadas(Date inicio) {
        return this.findVentasRealizadas(inicio, null);
    }

    private String buildWhereClauseStringFromStartEndDates(Date inicio, Date fin) {
        String whereClause;
        StringBuilder sb = new StringBuilder();
        String sInicio = null;
        String sFin = null;
        
        if (inicio != null) {
            sInicio = FormatoFechas.dateAStringLocal(inicio);
        }
        if (fin != null) {
            sFin = FormatoFechas.dateAStringLocal(fin);
        }
        
            
        if (sInicio != null && sFin != null) {
            sb = sb.append("fecha BETWEEN TO_DATE('")
                    .append(sInicio)
                    .append("') AND TO_DATE('")
                    .append(sFin)
                    .append("')");
        } else if (sInicio != null) {
            sb = sb.append("fecha > TO_DATE('")
                .append(sInicio)
                .append("')");
        } else if (sFin != null) {
            sb = sb.append("fecha < TO_DATE('")
                .append(sFin)
                .append("')");
        } else {
            return null;
        }
        
        whereClause = sb.toString();
        return whereClause;
    }

    @Override
    public Long findVentasRealizadas(Date inicio, Date fin) {
        
        String sqlQuery = "SELECT COUNT(1) FROM venta";
        String whereClause = this.buildWhereClauseStringFromStartEndDates(inicio, fin);
        
        StringBuilder queryBuilder = new StringBuilder().append(sqlQuery);
        if (whereClause != null) {
            queryBuilder.append(" WHERE ").append(whereClause);
        }
        
        LOG.debug("SQL Query: "+queryBuilder.toString());
        try {
            BigDecimal result = (BigDecimal)entityManager.createNativeQuery(queryBuilder.toString()).getSingleResult();
            Long count = result.longValue();
            return count;
        } catch (Exception exc) {
            LOG.error("No se pudo ejecutar la consulta. Traza del error: ", exc);
            return 0L;
        }
    }

    @Override
    public Long findUnidadesDeProductoVendidas(Producto producto) {
        return this.findUnidadesDeProductoVendidas(producto, null, null);
    }

    @Override
    public Long findUnidadesDeProductoVendidas(Producto producto, Date inicio) {
        return this.findUnidadesDeProductoVendidas(producto, inicio, null);
    }

    @Override
    public Long findUnidadesDeProductoVendidas(Producto producto, Date inicio, Date fin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
