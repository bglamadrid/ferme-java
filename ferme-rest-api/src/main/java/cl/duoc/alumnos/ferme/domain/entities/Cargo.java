package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.dto.CargoDTO;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Benjamin Guillermo
 */
@Entity
@Table(name = "CARGO")
@NamedQueries({
    @NamedQuery(name = "Cargo.findAll", query = "SELECT c FROM Cargo c")})
public class Cargo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_CARGO")
    @SequenceGenerator(name = "cargo_seq", sequenceName = "SEQ_CARGO", initialValue = 1, allocationSize = Ferme.DEFAULT_HIBERNATE_SEQUENCES_ALLOCATION_SIZE)
    @GeneratedValue(generator = "cargo_seq", strategy = GenerationType.AUTO)
    private Integer id;
    
    @Size(min = 1, max = 100)
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public Cargo() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public CargoDTO toDTO() {
        CargoDTO dto = new CargoDTO();
        dto.setIdCargo(id);
        dto.setDescripcionCargo(descripcion);
        return dto;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cargo)) {
            return false;
        }
        Cargo other = (Cargo) object;
        return (Objects.equals(this.id, other.getId()));
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Cargo[ id=" + id + " ]";
    }
    
}
