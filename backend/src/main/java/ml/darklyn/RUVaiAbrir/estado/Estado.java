package ml.darklyn.RUVaiAbrir.estado;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date date;
	
	public Estado() { }

	public EstadoRU getEstadoRU() {
		return estadoRU;
	}

	public void setEstadoRU(EstadoRU estadoRU) {
		this.estadoRU = estadoRU;
	}

	public TipoDeRefeicao getTipoDeRefeicao() {
		return tipoDeRefeicao;
	}

	public void setTipoDeRefeicao(TipoDeRefeicao tipoDeRefeicao) {
		this.tipoDeRefeicao = tipoDeRefeicao;
	}

	public Integer getAbertoQtd() {
		return abertoQtd;
	}

	public void setAbertoQtd(Integer abertoQtd) {
		this.abertoQtd = abertoQtd;
	}

	public Integer getFechadoQtd() {
		return fechadoQtd;
	}

	public void setFechadoQtd(Integer fechadoQtd) {
		this.fechadoQtd = fechadoQtd;
	}
	
	

}
