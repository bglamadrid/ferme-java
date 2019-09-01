package cl.duoc.alumnos.ferme.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.duoc.alumnos.ferme.entities.Proveedor;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public class ProveedorDTO extends PersonaDTO {
    @SuppressWarnings("unused")
	private final static Logger LOG = LoggerFactory.getLogger(ProveedorDTO.class);
    
    private Integer idProveedor;
    private String razonSocialProveedor;

    public ProveedorDTO() {
        super();
    }

    public ProveedorDTO(Integer idPersona, String nombreCompletoPersona, String rutPersona, String direccionPersona,
			String emailPersona, Long fonoPersona1, Long fonoPersona2, Long fonoPersona3) {
		super(idPersona, nombreCompletoPersona, rutPersona, direccionPersona, emailPersona, fonoPersona1, fonoPersona2,
				fonoPersona3);
	}

	public ProveedorDTO(Integer idPersona, String nombreCompletoPersona, String rutPersona, String direccionPersona,
			String emailPersona, Long fonoPersona1, Long fonoPersona2, Long fonoPersona3, Integer idProveedor,
			String razonSocialProveedor) {
		super(idPersona, nombreCompletoPersona, rutPersona, direccionPersona, emailPersona, fonoPersona1, fonoPersona2,
				fonoPersona3);
		this.idProveedor = idProveedor;
		this.razonSocialProveedor = razonSocialProveedor;
	}

	public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getRazonSocialProveedor() {
        return razonSocialProveedor;
    }

    public void setRazonSocialProveedor(String razonSocialProveedor) {
        this.razonSocialProveedor = razonSocialProveedor;
    }
    
    public Proveedor toEntity() {
        Proveedor entity = new Proveedor();
        if (idProveedor != null && idProveedor != 0) {
            entity.setId(idProveedor);
        }
        entity.setRazonSocial(razonSocialProveedor);
        
        entity.setPersona(this.personaToEntity());
        
        return entity;
    }

    @Override
    public String toString() {
        return "ProveedorDTO{" + "idProveedor=" + idProveedor + ", razonSocialProveedor=" + razonSocialProveedor + ", " + super.toString() + '}';
    }
    
}
