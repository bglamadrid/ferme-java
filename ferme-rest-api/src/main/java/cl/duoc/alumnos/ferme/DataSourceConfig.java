package cl.duoc.alumnos.ferme;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import cl.duoc.alumnos.ferme.entities.Cargo;
import cl.duoc.alumnos.ferme.entities.Cliente;
import cl.duoc.alumnos.ferme.entities.DetalleOrdenCompra;
import cl.duoc.alumnos.ferme.entities.DetalleVenta;
import cl.duoc.alumnos.ferme.entities.Empleado;
import cl.duoc.alumnos.ferme.entities.FamiliaProducto;
import cl.duoc.alumnos.ferme.entities.OrdenCompra;
import cl.duoc.alumnos.ferme.entities.Persona;
import cl.duoc.alumnos.ferme.entities.Producto;
import cl.duoc.alumnos.ferme.entities.Proveedor;
import cl.duoc.alumnos.ferme.entities.Rubro;
import cl.duoc.alumnos.ferme.entities.Sesion;
import cl.duoc.alumnos.ferme.entities.TipoProducto;
import cl.duoc.alumnos.ferme.entities.Usuario;
import cl.duoc.alumnos.ferme.entities.Venta;
import cl.duoc.alumnos.ferme.jpa.repositories.ICargosRepository;
import cl.duoc.alumnos.ferme.jpa.repositories.IClientesRepository;
import cl.duoc.alumnos.ferme.jpa.repositories.IDetallesOrdenesCompraRepository;
import cl.duoc.alumnos.ferme.jpa.repositories.IDetallesVentasRepository;
import cl.duoc.alumnos.ferme.jpa.repositories.IEmpleadosRepository;
import cl.duoc.alumnos.ferme.jpa.repositories.IFamiliasProductosRepository;
import cl.duoc.alumnos.ferme.jpa.repositories.IOrdenesCompraRepository;
import cl.duoc.alumnos.ferme.jpa.repositories.IPersonasRepository;
import cl.duoc.alumnos.ferme.jpa.repositories.IProductosRepository;
import cl.duoc.alumnos.ferme.jpa.repositories.IReportesRepository;
import cl.duoc.alumnos.ferme.jpa.repositories.IRubrosRepository;
import cl.duoc.alumnos.ferme.jpa.repositories.ISesionesRepository;
import cl.duoc.alumnos.ferme.jpa.repositories.ITiposProductosRepository;
import cl.duoc.alumnos.ferme.jpa.repositories.IUsuariosRepository;
import cl.duoc.alumnos.ferme.jpa.repositories.IVentasRepository;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Configuration
@EntityScan(basePackageClasses = {
		Cargo.class,
		Cliente.class,
		DetalleOrdenCompra.class,
		DetalleVenta.class,
		Empleado.class,
		FamiliaProducto.class,
		OrdenCompra.class,
		Persona.class,
		Producto.class,
		Proveedor.class,
		Rubro.class,
		Sesion.class,
		TipoProducto.class,
		Usuario.class,
		Venta.class
})
@EnableJpaRepositories(basePackageClasses = {
		ICargosRepository.class,
		IClientesRepository.class,
		IDetallesOrdenesCompraRepository.class,
		IDetallesVentasRepository.class,
		IEmpleadosRepository.class,
		IFamiliasProductosRepository.class,
		IOrdenesCompraRepository.class,
		IPersonasRepository.class,
		IProductosRepository.class,
		IReportesRepository.class,
		IRubrosRepository.class,
		ISesionesRepository.class,
		ITiposProductosRepository.class,
		IUsuariosRepository.class,
		IVentasRepository.class
})
public class DataSourceConfig {
}
