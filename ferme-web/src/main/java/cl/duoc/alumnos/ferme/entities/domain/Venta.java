/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.alumnos.ferme.entities.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Benjamin Guillermo
 */
@Entity
@Table(name = "VENTA")
@NamedQueries({
    @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v")})
public class Venta implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_VENTA")
    private int idVenta;
    
    @Column(name = "ID_EMPLEADO")
    private int idEmpleado;
    
    @Column(name = "ID_CLIENTE")
    private int idCliente;
    
    @Column(name = "TIPO_VENTA")
    private Character tipoVenta;
    
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    
    @Column(name = "SUBTOTAL")
    private long subtotal;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venta")
    private List<DetalleVenta> detalles;

    public Venta() {}

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Character getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(Character tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(long subtotal) {
        this.subtotal = subtotal;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.idVenta;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Venta)) {
            return false;
        }
        final Venta other = (Venta) object;
        return (this.idVenta != other.idVenta);
    }
    
    

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Venta[ idVenta=" + idVenta + " ]";
    }
    
}
