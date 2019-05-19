package cl.duoc.alumnos.ferme.domain.repositories;

import cl.duoc.alumnos.ferme.domain.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author got12
 */
@Component
@Repository
public interface IClientesRepository extends JpaRepository<Cliente, Integer>, QuerydslPredicateExecutor<Cliente> {
    
}
