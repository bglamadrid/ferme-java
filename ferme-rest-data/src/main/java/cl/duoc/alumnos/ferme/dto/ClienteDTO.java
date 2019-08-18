package cl.duoc.alumnos.ferme.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.duoc.alumnos.ferme.entities.Cliente;

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

	public ClienteDTO(Integer idPersona, String nombreCompletoPersona, String rutPersona, String direccionPersona,
			String emailPersona, Long fonoPersona1, Long fonoPersona2, Long fonoPersona3) {
		super(idPersona, nombreCompletoPersona, rutPersona, direccionPersona, emailPersona, fonoPersona1, fonoPersona2,
				fonoPersona3);
	}

	public ClienteDTO(Integer idPersona, String nombreCompletoPersona, String rutPersona, String direccionPersona,
			String emailPersona, Long fonoPersona1, Long fonoPersona2, Long fonoPersona3, Integer idCliente) {
		super(idPersona, nombreCompletoPersona, rutPersona, direccionPersona, emailPersona, fonoPersona1, fonoPersona2,
				fonoPersona3);
		this.idCliente = idCliente;
	}

//    public ClienteDTO(PersonaDTO dto) throws NullPointerException {
//    	setIdPersona(dto.getIdPersona());
//    	setRutPersona(dto.getRutPersona());
//    	setNombreCompletoPersona(dto.getNombreCompletoPersona());
//    	setDireccionPersona(dto.getDireccionPersona());
//    	setEmailPersona(dto.getEmailPersona());
//    	setFonoPersona1(dto.getFonoPersona1());
//    	setFonoPersona2(dto.getFonoPersona2());
//    	setFonoPersona3(dto.getFonoPersona3());
//    }

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
