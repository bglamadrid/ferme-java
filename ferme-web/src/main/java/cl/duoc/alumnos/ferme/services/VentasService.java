package cl.duoc.alumnos.ferme.services;

import cl.duoc.alumnos.ferme.domain.entities.Venta;
import cl.duoc.alumnos.ferme.domain.repositories.IVentasRepository;
import cl.duoc.alumnos.ferme.dto.VentaDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IVentasService;
import com.querydsl.core.types.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author got12
 */
@Service
public class VentasService implements IVentasService {

    @Autowired private IVentasRepository vtaRepo;
    
    @Override
    public Collection<VentaDTO> getVentas(int pageSize, int pageIndex, Predicate condicion) {
        Pageable pgbl = PageRequest.of(pageIndex, pageSize);
        
        List<VentaDTO> pagina = new ArrayList<>();
        Iterable<Venta> productos;
        
        if (condicion == null) {
            productos = this.vtaRepo.findAll(pgbl);
        } else {
            productos = this.vtaRepo.findAll(condicion, pgbl);
        }
        
        productos.forEach((entity) -> {
            VentaDTO dto = entity.toDTO();
            pagina.add(dto);
        });
        
        
        return pagina;
    }

    @Override
    public Predicate queryParamsMapToVentasFilteringPredicate(Map<String, String> queryParamsMap) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int saveVenta(VentaDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteVenta(Integer ventaId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
