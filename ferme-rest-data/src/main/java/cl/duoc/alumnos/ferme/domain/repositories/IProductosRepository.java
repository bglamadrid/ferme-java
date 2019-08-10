package cl.duoc.alumnos.ferme.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cl.duoc.alumnos.ferme.domain.entities.Producto;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Component
@Repository
public interface IProductosRepository extends JpaRepository<Producto, Integer>, QuerydslPredicateExecutor<Producto> {
    
}
