package cl.duoc.alumnos.ferme.domain.repositories;

import cl.duoc.alumnos.ferme.domain.entities.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
@Component
@Repository
public interface ISesionesRepository extends JpaRepository<Sesion, Integer>, QuerydslPredicateExecutor<Sesion> {
    
    @Query("SELECT s FROM Sesion s WHERE s._hash = :hash AND s._cerrada IS NULL")
    public Iterable<Sesion> findNotCerradaByHash(@Param("hash") String hash);
}
