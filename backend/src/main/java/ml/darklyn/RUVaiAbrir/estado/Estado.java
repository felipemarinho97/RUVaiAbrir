package ml.darklyn.RUVaiAbrir.estado;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import ml.darklyn.RUVaiAbrir.enumeration.EstadoRU;
import ml.darklyn.RUVaiAbrir.enumeration.TipoDeRefeicao;

@Entity
public class Estado {
	
	@Enumerated
	@NotNull
	private EstadoRU estadoRU;
	@Enumerated
	private TipoDeRefeicao tipoDeRefeicao;
	@Transient
	private Integer abertoQtd;
	@Transient
	private Integer fechadoQtd;

}
