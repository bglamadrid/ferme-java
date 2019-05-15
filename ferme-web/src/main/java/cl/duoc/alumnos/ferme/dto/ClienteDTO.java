package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.Cliente;

/**
 *
 * @author Benjamin Guillermo
 */
public class ClienteDTO extends PersonaDTO {

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
        if (idCliente != 0) {
            entity.setId(idCliente);
        }
        
        entity.setPersona(this.personaToEntity());
        
        return entity;
    }
    
}
