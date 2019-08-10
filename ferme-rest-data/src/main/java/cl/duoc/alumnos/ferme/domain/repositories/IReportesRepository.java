package cl.duoc.alumnos.ferme.domain.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.duoc.alumnos.ferme.domain.entities.Producto;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public interface IReportesRepository {
    
    @Query("SELECT COUNT(v) FROM Venta v")
    public Long findVentasRealizadas();
    
    @Query("SELECT COUNT(v) FROM Venta v "
            + "WHERE v._fecha > :inicio")
    public Long findVentasRealizadas(@Param("inicio") Date inicio);
    
    @Query("SELECT COUNT(v) FROM Venta "
            + "WHERE v._fecha BETWEEN :inicio AND :fin")
    public Long findVentasRealizadas(@Param("inicio") Date inicio, @Param("fin") Date fin);
    
    
    @Query("SELECT SUM(d._unidades) FROM DetalleVenta d "
            + "WHERE d._producto.id = (:producto).id")
    public Long findUnidadesDeProductoVendidas(
            @Param("producto") Producto producto
    );
    
    @Query("SELECT SUM(d._unidades) FROM Venta v "
            + "JOIN v._detalles d "
            + "WHERE v._fecha :inicio "
            + "AND d._producto.id = (:producto).id")
    public Long findUnidadesDeProductoVendidas(
            @Param("producto") Producto producto, 
            @Param("inicio") Date inicio
    );
    
    @Query("SELECT SUM(d._unidades) FROM Venta v "
            + "JOIN v._detalles d "
            + "WHERE v._fecha BETWEEN :inicio AND :fin "
            + "AND d._producto.id = (:producto).id")
    public Long findUnidadesDeProductoVendidas(
            @Param("producto") Producto producto, 
            @Param("inicio") Date inicio, 
            @Param("fin") Date fin
    );
}