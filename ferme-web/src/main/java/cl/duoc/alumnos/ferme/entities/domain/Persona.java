package cl.duoc.alumnos.ferme.entities.domain;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Benjamin Guillermo
 */
@Entity
@Table(name = "PERSONA")
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p")})
public class Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_PERSONA")
    private int idPersona;
    
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE_COMPLETO")
    private String nombreCompleto;
    
    @Size(min = 1, max = 20)
    @Column(name = "RUT")
    private String rut;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persona")
    private Proveedor proveedor;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persona")
    private Empleado empleado;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persona")
    private Cliente cliente;
    
    @OneToOne(mappedBy = "persona")
    private Usuario usuario;

    public Persona() {}

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.idPersona;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Persona)) {
            return false;
        }
        final Persona other = (Persona) object;
        return (this.idPersona == other.idPersona);
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Persona[ idPersona=" + idPersona + " ]";
    }
    
}
