package cl.duoc.alumnos.ferme.domain.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "CLIENTE")
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")})
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_CLIENTE")
    private int id;
    
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private Persona persona;
    
    @Size(min = 1, max = 200)
    @Column(name = "DIRECCION")
    private String direccion;
    
    @Size(min = 1, max = 100)
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "FONO1")
    private Long fono1;
    
    @Column(name = "FONO2")
    private Long fono2;
    
    @Column(name = "FONO3")
    private Long fono3;

    public Cliente() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getFono1() {
        return fono1;
    }

    public void setFono1(Long fono1) {
        this.fono1 = fono1;
    }

    public Long getFono2() {
        return fono2;
    }

    public void setFono2(Long fono2) {
        this.fono2 = fono2;
    }

    public Long getFono3() {
        return fono3;
    }

    public void setFono3(Long fono3) {
        this.fono3 = fono3;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cliente)) {
            return false;
        }
        final Cliente other = (Cliente) object;
        return (this.id == other.id);
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Cliente[ idCliente=" + id + " ]";
    }
    
}
