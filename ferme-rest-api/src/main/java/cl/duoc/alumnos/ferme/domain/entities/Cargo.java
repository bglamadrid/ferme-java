package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.FermeConfig;
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

/** Representa un registro de la tabla CARGO.
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Entity
@Table(name = "CARGO")
@NamedQueries({
    @NamedQuery(name = "Cargo.findAll", query = "SELECT c FROM Cargo c")})
public class Cargo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_CARGO")
    @SequenceGenerator(name = "cargo_seq", sequenceName = "SEQ_CARGO", initialValue = 1, allocationSize = FermeConfig.ESPACIO_ASIGNACION_SECUENCIAS_HIBERNATE)
    @GeneratedValue(generator = "cargo_seq", strategy = GenerationType.AUTO)
    private Integer _id;
    
    @Size(min = 1, max = 100)
    @Column(name = "DESCRIPCION")
    private String _descripcion;

    public Cargo() {
        super();
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        this._id = id;
    }

    public String getDescripcion() {
        return _descripcion;
    }

    public void setDescripcion(String descripcion) {
        this._descripcion = descripcion;
    }
    
    public CargoDTO toDTO() {
        CargoDTO dto = new CargoDTO();
        dto.setIdCargo(_id);
        dto.setDescripcionCargo(_descripcion);
        
        return dto;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this._id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cargo)) {
            return false;
        }
        Cargo other = (Cargo) object;
        return (Objects.equals(this._id, other.getId()));
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Cargo[ id=" + _id + " ]";
    }
    
}
