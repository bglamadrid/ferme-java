package cl.duoc.alumnos.ferme.domain.repositories;

import cl.duoc.alumnos.ferme.domain.entities.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
@Component
@Repository
public interface IFunctionsRepository extends CrudRepository<Producto, Integer> {
    
    @Query(nativeQuery = true, value = "SELECT FERME.FNC_GET_PRODUCTO_CODIGO(:id) FROM DUAL")
    String getProductoCodigo(@Param("id") Integer productoId);
    
}
