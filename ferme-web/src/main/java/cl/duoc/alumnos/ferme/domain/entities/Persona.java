package cl.duoc.alumnos.ferme.domain.entities;

import java.io.Serializable;
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
    private int id;
    
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE_COMPLETO")
    private String nombreCompleto;
    
    @Size(min = 1, max = 20)
    @Column(name = "RUT")
    private String rut;
    
    @OneToOne(mappedBy = "persona")
    private Usuario usuario;

    public Persona() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Persona)) {
            return false;
        }
        final Persona other = (Persona) object;
        return (this.id == other.id);
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Persona[ idPersona=" + id + " ]";
    }
    
}
