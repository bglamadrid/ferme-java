package cl.duoc.alumnos.ferme.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.duoc.alumnos.ferme.domain.entities.Cliente;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public class ClienteDTO extends PersonaDTO {
    @SuppressWarnings("unused")
	private final static Logger LOG = LoggerFactory.getLogger(ClienteDTO.class);

    private Integer idCliente;

    public ClienteDTO() {
        super();
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }
    
    public Cliente toEntity() {
        Cliente entity = new Cliente();
        if (idCliente != null && idCliente != 0) {
            entity.setId(idCliente);
        }
        
        entity.setPersona(this.personaToEntity());
        
        return entity;
    }

    @Override
    public String toString() {
        return "ClienteDTO{" + "idCliente=" + idCliente + ", " + super.toString() + '}';
    }
    
}
